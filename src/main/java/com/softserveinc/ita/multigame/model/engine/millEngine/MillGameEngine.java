package com.softserveinc.ita.multigame.model.engine.millEngine;


import com.softserveinc.ita.multigame.model.engine.GameState;
import com.softserveinc.ita.multigame.model.engine.GenericGameEngine;

import java.util.ArrayList;
import java.util.List;

public class MillGameEngine extends GenericGameEngine<String> {
    private Board board = new Board();
    private MillPlayer millPlayer1 = new MillPlayer("white");
    private MillPlayer millPlayer2 = new MillPlayer("black");

    @Override
    public String getBoard() {
        return board.drawField();
    }

    @Override //TODO change GenericGameEngine
    protected boolean validateTurnOrder(String player) {
        boolean firstPlayerCorrectOrder = firstPlayer.equals(player)
                && (gameState == GameState.WAIT_FOR_FIRST_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_FIRST_PLAYER_DROP);

        boolean secondPlayerCorrectOrder = secondPlayer.equals(player)
                && (gameState == GameState.WAIT_FOR_SECOND_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_SECOND_PLAYER_DROP);
        return firstPlayerCorrectOrder || secondPlayerCorrectOrder;
    }


    @Override
    protected boolean validateTurnSyntax(String turn) {
        String command;

        if (turn.startsWith(Commands.PUT_NEW_STONE)) {
            command = Commands.PUT_NEW_STONE;
        } else if (turn.startsWith(Commands.DROP_STONE)) {
            command = Commands.DROP_STONE;
        } else if (turn.startsWith(Commands.REPLACE_STONE)) {
            command = Commands.REPLACE_STONE;
        } else {
            return false;
        }

        turn = turn.substring(command.length()).trim();

        String[] split = turn.split("[ ]+");

        if (command.equals(Commands.REPLACE_STONE)) {
            if (split.length != 2) {
                return false;
            }
        } else {
            if (split.length != 1) {
                return false;
            }
        }

        boolean isDigit = true;
        for (String s : split) {
            isDigit = isDigit && isDigitWithin1To23(s);
        }
        return isDigit;
    }

    @Override
    protected boolean validateTurnLogic(String turn) {
        MillPlayer player;
        if (gameState == GameState.WAIT_FOR_FIRST_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_FIRST_PLAYER_DROP){
            player = millPlayer1;
        } else {
            player = millPlayer2;
        }

        if (turn.startsWith(Commands.PUT_NEW_STONE)) {
            return putLogicValidation(player, turn);
        }
        if (turn.startsWith(Commands.REPLACE_STONE)) {
            return replaceLogicValidation(player, turn);
        }
        if (turn.startsWith(Commands.DROP_STONE)) {
            return dropLogicValidation(player, turn);
        }

        return false;
    }

    @Override
    protected boolean validatePlayer(String player) {
        return true;
    }

    @Override
    protected int changeGameState(String player, String turn) {
        doTurn(player, turn);

        if (board.isNewMillCreate()) {
            if (player.equals(getFirstPlayer())) {
                return GameState.WAIT_FOR_FIRST_PLAYER_DROP;
            } else {
                return GameState.WAIT_FOR_SECOND_PLAYER_DROP;
            }
        }

        if (millPlayer1.getCountOfStones() < 3) {
            return GameState.FINISHED_WITH_SECOND_PLAYER_AS_A_WINNER;
        }
        if (millPlayer2.getCountOfStones() < 3) {
            return GameState.FINISHED_WITH_FIRST_PLAYER_AS_A_WINNER;
        }

        if (player.equals(getFirstPlayer())) {
            return GameState.WAIT_FOR_SECOND_PLAYER_TURN;
        } else {
            return GameState.WAIT_FOR_FIRST_PLAYER_TURN;
        }
    }

    void doTurn(String player, String turn) {
        MillPlayer currentPlayer;
        MillPlayer opponent;

        if (player.equals(getFirstPlayer())) {
            currentPlayer = millPlayer1;
            opponent = millPlayer2;
        } else {
            currentPlayer = millPlayer2;
            opponent = millPlayer1;
        }

        int[] positions = turnParser(turn);

        if (turn.startsWith(Commands.PUT_NEW_STONE)) {
            putStone(currentPlayer.getNewStone(), positions[0]);
        }
        if (turn.startsWith(Commands.REPLACE_STONE)) {
            replaceStone(positions[0], positions[1]);
        }
        if (turn.startsWith(Commands.DROP_STONE)) {
            opponent.dropStone(dropStone(positions[0]));
        }
    }

    //only for test;
    void setGameState(int state){
        gameState = state;
    }
    //only for test;
    void setBoard(Board board) {
        this.board = board;
    }
    //only for test;
    void setMillPlayer1(MillPlayer millPlayer1) {
        this.millPlayer1 = millPlayer1;
    }
    //only for test;
    void setMillPlayer2(MillPlayer millPlayer2) {
        this.millPlayer2 = millPlayer2;
    }


    private void putStone(Stone stone, int position) {
        Position p = board.getPosition(position);
        p.setStone(stone);
    }

    private void replaceStone(int oldPosition, int newPosition) {
        Position oldP = board.getPosition(oldPosition);
        Stone stone = oldP.getStone();
        oldP.setStone(null);
        board.isMillDestroy();
        Position newP = board.getPosition(newPosition);
        newP.setStone(stone);
    }

    private Stone dropStone(int position) {
        Position p = board.getPosition(position);
        Stone stone = p.getStone();
        p.setStone(null);
        board.isMillDestroy();
        return stone;
    }

    private int[] turnParser(String turn) {
        String command = null;

        if (turn.startsWith(Commands.PUT_NEW_STONE)) {
            command = Commands.PUT_NEW_STONE;
        }
        if (turn.startsWith(Commands.DROP_STONE)) {
            command = Commands.DROP_STONE;
        }
        if (turn.startsWith(Commands.REPLACE_STONE)) {
            command = Commands.REPLACE_STONE;
        }

        turn = turn.substring(command.length()).trim();

        String[] split = turn.split("[ ]+");
        int[] position = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            position[i] = Integer.parseInt(split[i]);
        }

        return position;
    }

    private boolean isDigitWithin1To23(String string) {
        try {
            int i = Integer.parseInt(string);
            return (i >= 0) && (i <= 23);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean putLogicValidation(MillPlayer player, String turn) {
        int[] positions = turnParser(turn);
        Position p = board.getPosition(positions[0]);
        return  (gameState == GameState.WAIT_FOR_FIRST_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_SECOND_PLAYER_TURN)
                && player.getCountOfStonesInHand() > 0
                && p.getStone() == null;
    }

    private boolean replaceLogicValidation(MillPlayer player, String turn) {
        int[] positions = turnParser(turn);
        Position oldP = board.getPosition(positions[0]);
        Position newP = board.getPosition(positions[1]);
        return  (gameState == GameState.WAIT_FOR_FIRST_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_SECOND_PLAYER_TURN)
                && player.getCountOfStonesInHand() == 0
                && oldP.getStone() != null
                && oldP.getStone().getColor().equals(player.getColor())
                && newP.getStone() == null
                && (oldP.getNeighbors().contains(newP) || player.getCountOfStones() == 3);
    }

    private boolean dropLogicValidation(MillPlayer player, String turn) {
        int[] positions = turnParser(turn);
        Position p = board.getPosition(positions[0]);

        boolean isAllPositionInMill = true;
        for (Position position : board.getPositions()){
            if (position.getStone() != null && !position.getStone().getColor().equals(player.getColor())){
                isAllPositionInMill = isAllPositionInMill && board.isPositionInMill(position);
            }
        }
        return (gameState == GameState.WAIT_FOR_FIRST_PLAYER_DROP
                || gameState == GameState.WAIT_FOR_SECOND_PLAYER_DROP)
                && p.getStone() != null
                && !p.getStone().getColor().equals(player.getColor())
                && (!board.isPositionInMill(p) || isAllPositionInMill);
    }


    //TODO change it in group project
    public boolean isCurrentPlayer(String player){
        return  (gameState == GameState.WAIT_FOR_FIRST_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_FIRST_PLAYER_DROP)
                && firstPlayer.equals(player)
                ||(gameState == GameState.WAIT_FOR_SECOND_PLAYER_TURN
                || gameState == GameState.WAIT_FOR_SECOND_PLAYER_DROP)
                && secondPlayer.equals(player) ;

    }

    //TODO change it in group project
    public List<String> getStonesList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < millPlayer1.getCountOfStonesInHand(); i++) {
            list.add("W");
        }
        for (int i = 0; i < millPlayer2.getCountOfStonesInHand(); i++) {
            list.add("B");
        }
        return list;
    }

}
