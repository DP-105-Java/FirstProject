package com.softserveinc.ita.multigame.model;


import com.softserveinc.ita.multigame.model.engine.GameResultCode;
import com.softserveinc.ita.multigame.model.engine.millEngine.MillGameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GameTest {
    private MillGameEngine mill = mock(MillGameEngine.class);
    private Player player = mock(Player.class);
    private Game game;


    @Before
    public void setUp() {
        when(player.getLogin()).thenReturn("1");
        game = new Game(player);
        game.setGame(mill);
    }

    @Test
    public void whenGetId(){
        when(mill.getId()).thenReturn(1L);

        assertSame(1L, game.getId());
        verify(mill).getId();
    }

    @Test
    public void whenJoinToGame(){
        when(mill.getSecondPlayer()).thenReturn(null);

        assertTrue(game.joinToGame(player));
        verify(mill).getSecondPlayer();
        verify(mill).setSecondPlayer("1");
        verify(player, times(2)).getLogin();

    }

    @Test
    public void whenGetFirstPlayerLogin(){
        when(mill.getFirstPlayer()).thenReturn("1");

        assertEquals("1", game.getFirstPlayerLogin());
        verify(mill).getFirstPlayer();
    }

    @Test
    public void whenGetSecondPlayerLogin(){
        when(mill.getSecondPlayer()).thenReturn("1");

        assertEquals("1", game.getSecondPlayerLogin());
        verify(mill).getSecondPlayer();
    }

    @Test
    public void whenMakeTurn(){
        when(mill.makeTurn("1", "p5")).thenReturn(true);

        assertTrue(game.makeTurn(player, "p5"));
        verify(mill).makeTurn("1", "p5");
        verify(player, times(2)).getLogin();
    }

    @Test
    public void whenWhatIsWrongMessageAndGameIsFinished(){
        when(mill.isFinished()).thenReturn(true);
        when(mill.getTheWinner()).thenReturn("1");

        assertEquals("Game Over. Congratulation 1", game.whatIsWrongMessage(player));
        verify(mill).isFinished();
        verify(mill).getTheWinner();
    }

    @Test
    public void whenWhatIsWrongMessageAndNotCurrentPlayerMakeTurn(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(true);
        when(mill.isCurrentPlayer("1")).thenReturn(false);

        assertEquals("", game.whatIsWrongMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill).isCurrentPlayer("1");
        verify(player, times(2)).getLogin();
    }

    @Test
    public void whenWhatIsWrongMessageAndGameIsNotStarting(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(false);
        when(mill.isCurrentPlayer("1")).thenReturn(true);

        assertEquals("", game.whatIsWrongMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill, never()).isCurrentPlayer("1");
        verify(player, times(1)).getLogin();
    }

    @Test
    public void whenWhatIsWrongMessageAndGetResultCodeBatTurnLogic(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(true);
        when(mill.isCurrentPlayer("1")).thenReturn(true);
        when(mill.getResultCode()).thenReturn(GameResultCode.BAD_TURN_LOGIC);

        assertEquals("Wrong command. Read rules and try again!", game.whatIsWrongMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill).isCurrentPlayer("1");
        verify(player, times(2)).getLogin();
        verify(mill).getResultCode();
    }
    @Test
    public void whenWhatIsWrongMessageAndGetResultCodeBatTurnSyntax(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(true);
        when(mill.isCurrentPlayer("1")).thenReturn(true);
        when(mill.getResultCode()).thenReturn(GameResultCode.BAD_TURN_SYNTAX);

        assertEquals("Mistake in command. Please try again!", game.whatIsWrongMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill).isCurrentPlayer("1");
        verify(player, times(2)).getLogin();
        verify(mill).getResultCode();
    }
    @Test
    public void whenWhatIsWrongMessageAndGetResultCodeNotBatTurnLogicAndNotBatTurnSyntax(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(true);
        when(mill.isCurrentPlayer("1")).thenReturn(true);
        when(mill.getResultCode()).thenReturn(GameResultCode.BAD_PLAYER);

        assertEquals("", game.whatIsWrongMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill).isCurrentPlayer("1");
        verify(player, times(2)).getLogin();
        verify(mill).getResultCode();
    }


    @Test
    public void whenWhichTurnMessageAndGameIsFinished(){
        when(mill.isFinished()).thenReturn(true);
        when(mill.getTheWinner()).thenReturn("1");

        assertEquals("Game Over. Congratulation 1", game.whichTurnMessage(player));
        verify(mill).isFinished();
        verify(mill).getTheWinner();
    }

    @Test
    public void whenWhichTurnMessageIsNotStarted(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(false);

        assertEquals("Please wait for your opponent", game.whichTurnMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
    }

    @Test
    public void whenWhichTurnMessageAndCurrentPlayerMakeTurn(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(true);
        when(mill.isCurrentPlayer("1")).thenReturn(true);

        assertEquals("Your turn", game.whichTurnMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill).isCurrentPlayer("1");
        verify(player, times(2)).getLogin();
    }

    @Test
    public void whenWhichTurnMessageAndNotCurrentPlayerMakeTurn(){
        when(mill.isFinished()).thenReturn(false);
        when(mill.isStarted()).thenReturn(true);
        when(mill.isCurrentPlayer("1")).thenReturn(false);

        assertEquals("Opponents turn", game.whichTurnMessage(player));
        verify(mill).isFinished();
        verify(mill).isStarted();
        verify(mill).isCurrentPlayer("1");
        verify(player, times(2)).getLogin();
    }

    @Test
    public void whenGetColorAndFirstPlayerMakeTurn(){
        when(mill.getFirstPlayer()).thenReturn("1");

        assertEquals("White" ,game.getColor(player));
        verify(mill).getFirstPlayer();
        verify(player, times(2)).getLogin();
    }

    @Test
    public void whenGetColorAndSecondPlayerMakeTurn(){
        when(mill.getSecondPlayer()).thenReturn("2");

        assertEquals("Black" ,game.getColor(player));
        verify(mill).getFirstPlayer();
        verify(player, times(2)).getLogin();
    }

    @Test
    public void whenGetStones(){
        List<String> s = new ArrayList<>();
        when(mill.getStonesList()).thenReturn(s);
        assertSame(s, game.getStones());
        verify(mill).getStonesList();

    }





}
