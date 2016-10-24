package com.softserveinc.ita.multigame.model.engine.millEngine;


import com.softserveinc.ita.multigame.model.engine.GameState;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class MillGameEngineTurnLogicValidationTest {
    private MillGameEngine mill = new MillGameEngine();
    private MillPlayer millPlayer;
    private Board board;
    private Position position;
    private Position position2;
    private Stone stone;
    private Stone stone2;

    @Before
    public void setUp() {
        millPlayer = mock(MillPlayer.class);
        board = mock(Board.class);
        position = mock(Position.class);
        position2 = mock(Position.class);
        stone = mock(Stone.class);
        stone2 = mock(Stone.class);

        mill.setFirstPlayer("first");
        mill.setSecondPlayer("second");
        mill.setMillPlayer1(millPlayer);
        mill.setBoard(board);
    }

    @Test
    public void WhenFirstPlayerMakeValidPut() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(1);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(null);

        boolean response = mill.validateTurnLogic(Commands.PUT_NEW_STONE + 5);

        assertTrue(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position).getStone();

    }

    @Test
    public void WhenFirstPlayerPutAndHeDoesNotHaveStonesInHand() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(0);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(null);

        boolean response = mill.validateTurnLogic(Commands.PUT_NEW_STONE + 5);

        assertFalse(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, never()).getStone();
    }

    @Test
    public void WhenFirstPlayerPutsStoneToNotFreePosition() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(1);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);

        boolean response = mill.validateTurnLogic(Commands.PUT_NEW_STONE + 5);

        assertFalse(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position).getStone();
    }

    @Test
    public void WhenTurnIsDropAddFirstPlayerPutsStone() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);
        when(millPlayer.getCountOfStonesInHand()).thenReturn(1);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(null);

        boolean response = mill.validateTurnLogic(Commands.PUT_NEW_STONE + 5);

        assertFalse(response);
        verify(millPlayer, never()).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, never()).getStone();
    }

    @Test
    public void WhenFirstPlayerHasStonesInHandAndReplaceHisStone() {

        when(millPlayer.getCountOfStonesInHand()).thenReturn(1);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.getPosition(6)).thenReturn(position2);
        when(position2.getStone()).thenReturn(null);
        List<Position> neighbors = new ArrayList<>();
        when(position.getNeighbors()).thenReturn(neighbors);
        neighbors.add(position2);

        boolean response = mill.validateTurnLogic(Commands.REPLACE_STONE + 5 + " " + 6);

        assertFalse(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, never()).getStone();
        verify(stone, never()).getColor();
        verify(board).getPosition(6);
        verify(position2, never()).getStone();
        verify(position, never()).getNeighbors();
    }


    @Test
    public void WhenFirstPlayerDoesNotHasStonesInHandAndReplaceHisStoneToFreeNeighborPosition() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(0);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.getPosition(6)).thenReturn(position2);
        when(position2.getStone()).thenReturn(null);
        List<Position> neighbors = new ArrayList<>();
        when(position.getNeighbors()).thenReturn(neighbors);
        neighbors.add(position2);

        boolean response = mill.validateTurnLogic(Commands.REPLACE_STONE + 5 + " " + 6);

        assertTrue(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(board).getPosition(6);
        verify(position2).getStone();
        verify(position).getNeighbors();
    }

    @Test
    public void WhenTurnIsDropAndFirstPlayerReplaceHisStone() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);

        when(millPlayer.getCountOfStonesInHand()).thenReturn(0);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.getPosition(6)).thenReturn(position2);
        when(position2.getStone()).thenReturn(null);
        List<Position> neighbors = new ArrayList<>();
        when(position.getNeighbors()).thenReturn(neighbors);
        neighbors.add(position2);

        boolean response = mill.validateTurnLogic(Commands.REPLACE_STONE + 5 + " " + 6);

        assertFalse(response);
        verify(millPlayer, never()).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, never()).getStone();
        verify(stone, never()).getColor();
        verify(board).getPosition(6);
        verify(position2, never()).getStone();
        verify(position, never()).getNeighbors();
    }

    @Test
    public void WhenFirstPlayerReplaceHisStoneToNotFreeNeighborPosition() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(0);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.getPosition(6)).thenReturn(position2);
        when(position2.getStone()).thenReturn(stone2);
        List<Position> neighbors = new ArrayList<>();
        when(position.getNeighbors()).thenReturn(neighbors);
        neighbors.add(position2);

        boolean response = mill.validateTurnLogic(Commands.REPLACE_STONE + 5 + " " + 6);

        assertFalse(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(board).getPosition(6);
        verify(position2).getStone();
        verify(position, never()).getNeighbors();
    }

    @Test
    public void WhenFirstPlayerReplaceHisStoneNotToNeighborPosition() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(0);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.getPosition(14)).thenReturn(position2);
        when(position2.getStone()).thenReturn(null);
        List<Position> neighbors = new ArrayList<>();
        when(position.getNeighbors()).thenReturn(neighbors);

        boolean response = mill.validateTurnLogic(Commands.REPLACE_STONE + 5 + " " + 14);

        assertFalse(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(board).getPosition(14);
        verify(position2).getStone();
        verify(position).getNeighbors();
    }

    @Test
    public void WhenFirstPlayerHasOnlyThreeStoneAndReplaceHisStoneNotToNeighborPosition() {
        when(millPlayer.getCountOfStonesInHand()).thenReturn(0);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.getPosition(14)).thenReturn(position2);
        when(position2.getStone()).thenReturn(null);
        List<Position> neighbors = new ArrayList<>();
        when(position.getNeighbors()).thenReturn(neighbors);
        when(millPlayer.getCountOfStones()).thenReturn(3);

        boolean response = mill.validateTurnLogic(Commands.REPLACE_STONE + 5 + " " + 14);

        assertTrue(response);
        verify(millPlayer).getCountOfStonesInHand();
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(board).getPosition(14);
        verify(position2).getStone();
        verify(position).getNeighbors();
        verify(millPlayer).getCountOfStones();
    }

    @Test
    public void WhenTurnIsNotDropAndFirstPlayerDropOpponentsStone() {
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position)).thenReturn(false);

        boolean response = mill.validateTurnLogic(Commands.DROP_STONE + 5);

        assertFalse(response);
        verify(board).getPosition(5);
        verify(position, never()).getStone();
        verify(stone, never()).getColor();
        verify(millPlayer, never()).getColor();
        verify(board, never()).isPositionInMill(position);


    }

    @Test
    public void WhenTurnIsDropAndFirstPlayerDropHisOwnStone() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position)).thenReturn(false);

        boolean response = mill.validateTurnLogic(Commands.DROP_STONE + 5);

        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(millPlayer).getColor();
        verify(board).isPositionInMill(position);
        assertTrue(response);
    }

    @Test
    public void WhenTurnIsDropAndFirstPlayerDropFreePosition() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(null);
        when(stone.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position)).thenReturn(false);

        boolean response = mill.validateTurnLogic(Commands.DROP_STONE + 5);

        assertFalse(response);
        verify(board).getPosition(5);
        verify(position, times(1)).getStone();
        verify(stone, never()).getColor();
        verify(millPlayer, never()).getColor();
        verify(board, never()).isPositionInMill(position);
    }

    @Test
    public void WhenTurnIsDropAndFirstPlayerDropOpponentsStone() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("www");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position)).thenReturn(false);

        boolean response = mill.validateTurnLogic(Commands.DROP_STONE + 5);

        assertFalse(response);
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(millPlayer).getColor();
        verify(board, never()).isPositionInMill(position);
    }

    @Test
    public void WhenTurnIsDropAndFirstPlayerDropOpponentsStoneInMill() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position)).thenReturn(true);
        List<Position> positions = new ArrayList<>();
        positions.add(position2);
        when(board.getPositions()).thenReturn(positions);
        when(position2.getStone()).thenReturn(stone2);
        when(stone2.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position2)).thenReturn(false);

        boolean response = mill.validateTurnLogic(Commands.DROP_STONE + 5);

        assertFalse(response);
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(millPlayer, times(2)).getColor();
        verify(board).isPositionInMill(position);
        verify(board).getPositions();
        verify(position2, times(2)).getStone();
        verify(stone2).getColor();
        verify(board).isPositionInMill(position2);
    }

    @Test
    public void WhenTurnIsDropAndFirstPlayerDropOpponentsStoneInMillBecauseThereIsNotAnotherStones() {
        mill.setGameState(GameState.WAIT_FOR_FIRST_PLAYER_DROP);
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(stone.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position)).thenReturn(true);
        List<Position> positions = new ArrayList<>();
        positions.add(position2);
        when(board.getPositions()).thenReturn(positions);
        when(position2.getStone()).thenReturn(stone2);
        when(stone2.getColor()).thenReturn("bbb");
        when(millPlayer.getColor()).thenReturn("www");
        when(board.isPositionInMill(position2)).thenReturn(true);

        boolean response = mill.validateTurnLogic(Commands.DROP_STONE + 5);

        assertTrue(response);
        verify(board).getPosition(5);
        verify(position, times(2)).getStone();
        verify(stone).getColor();
        verify(millPlayer, times(2)).getColor();
        verify(board).isPositionInMill(position);
        verify(board).getPositions();
        verify(position2, times(2)).getStone();
        verify(stone2).getColor();
        verify(board).isPositionInMill(position2);
    }

}
