package com.softserveinc.ita.multigame.model;


import com.softserveinc.ita.multigame.model.engine.GameResultCode;
import com.softserveinc.ita.multigame.model.engine.millEngine.MillGameEngine;

import java.util.List;

public class Game {
    private MillGameEngine game;

    public Game(Player player) {
        game = new MillGameEngine();
        game.setFirstPlayer(player.getLogin());
    }

    //only for test
    void setGame(MillGameEngine game) {
        this.game = game;
    }

    public Long getId(){
        return game.getId();
    }

    public boolean joinToGame(Player player){
        if (game.getSecondPlayer() == null){
            game.setSecondPlayer(player.getLogin());
            return true;
        }
        return false;
    }

    public String getFirstPlayerLogin(){
        return game.getFirstPlayer();
    }

    public String getSecondPlayerLogin(){
        return game.getSecondPlayer();
    }

    public boolean makeTurn(Player player, String turn){
        return game.makeTurn(player.getLogin(), turn);
    }

    public String getBoard(){
        return game.getBoard();
    }

    public String whatIsWrongMessage(Player player){
        if (game.isFinished()){
            return "Game Over. Congratulation " + game.getTheWinner();
        }
        if (game.isStarted() && game.isCurrentPlayer(player.getLogin())) {
            switch (game.getResultCode()) {
                case GameResultCode.BAD_TURN_LOGIC:
                    return "Wrong command. Read rules and try again!";
                case GameResultCode.BAD_TURN_SYNTAX:
                    return "Mistake in command. Please try again!";
                default:
                    return "";
            }
        }
        return "";
    }

    public String whichTurnMessage(Player player){
        if (game.isFinished()){
            return "Game Over. Congratulation " + game.getTheWinner();
        }
        if (game.isStarted()) {
            if (game.isCurrentPlayer(player.getLogin())) {
                return "Your turn";
            } else {
                return "Opponents turn";
            }
        } else {
            return "Please wait for your opponent";
        }
    }

    public String getColor(Player player){
        if (player.getLogin().equals(game.getFirstPlayer())){
            return "White";
        } else {
            return "Black";
        }
    }

    public List<String> getStones(){
        return game.getStonesList();
    }

    public boolean isFinished(){
        return game.isFinished();
    }
}
