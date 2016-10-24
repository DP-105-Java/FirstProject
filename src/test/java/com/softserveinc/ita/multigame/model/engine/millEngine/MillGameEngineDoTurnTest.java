package com.softserveinc.ita.multigame.model.engine.millEngine;


import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MillGameEngineDoTurnTest {
    private MillGameEngine mill = new MillGameEngine();
    private String firstPlayer = "first";
    private Board board;
    private MillPlayer millPlayer1;
    private MillPlayer millPlayer2;
    private Position position;
    private Position position2;
    private Stone stone;


    @Before
    public void setUp() {
        millPlayer1 = mock(MillPlayer.class);
        millPlayer2 = mock(MillPlayer.class);
        board = mock(Board.class);
        position = mock(Position.class);
        position2 = mock(Position.class);
        stone = mock(Stone.class);

        mill.setFirstPlayer(firstPlayer);

        mill.setMillPlayer1(millPlayer1);
        mill.setMillPlayer2(millPlayer2);
        mill.setBoard(board);
    }

    @Test
    public void WhenPlayerPutStone() {
        when(millPlayer1.getNewStone()).thenReturn(stone);
        when(board.getPosition(5)).thenReturn(position);

        mill.doTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);

        verify(millPlayer1).getNewStone();
        verify(board).getPosition(5);
        verify(position).setStone(stone);
    }

    @Test
    public void WhenPlayerReplaceStone() {
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);
        when(board.getPosition(6)).thenReturn(position2);

        mill.doTurn(firstPlayer, Commands.REPLACE_STONE + 5 + " " + 6);

        verify(board, times(2)).getPosition(anyInt());
        verify(position).getStone();
        verify(position).setStone(null);
        verify(position2).setStone(stone);
        verify(board).isMillDestroy();
    }

    @Test
    public void WhenPlayerDropStone() {
        when(board.getPosition(5)).thenReturn(position);
        when(position.getStone()).thenReturn(stone);

        mill.doTurn(firstPlayer, Commands.DROP_STONE + 5);

        verify(board).getPosition(5);
        verify(position).getStone();
        verify(position).setStone(null);
        verify(board).isMillDestroy();
        verify(millPlayer2).dropStone(stone);

    }
}
