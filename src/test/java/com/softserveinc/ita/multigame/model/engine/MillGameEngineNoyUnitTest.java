package com.softserveinc.ita.multigame.model.engine;

import com.softserveinc.ita.multigame.model.engine.millEngine.Commands;
import com.softserveinc.ita.multigame.model.engine.millEngine.MillGameEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MillGameEngineNoyUnitTest {
    private MillGameEngine mill = new MillGameEngine();
    private String firstPlayer = "first";
    private String secondPlayer = "second";

    @Before
    public void setUp() {
        mill.setFirstPlayer(firstPlayer);
        mill.setSecondPlayer(secondPlayer);
    }

    @Test
    public void WhenFirstPlayerMakeValidPut(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);

        int expected = GameResultCode.OK;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenSecondPlayerMakeValidPut(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);

        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);

        int expected = GameResultCode.OK;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenSecondPlayerPutHisStoneToNotFreePosition(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);

        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenIsNotDropTurnAndFirstPlayerDropStone(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);

        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 6);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenSecondPlayerMakePutOutOfTurn(){
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);

        int expected = GameResultCode.BAD_TURN_ORDER;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenSecondPlayerMakeReplaceOutOfTurn(){
        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 5 + " " + 6);

        int expected = GameResultCode.BAD_TURN_ORDER;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenSecondPlayerMakeDropOutOfTurn(){
        mill.makeTurn(secondPlayer, Commands.DROP_STONE + 5);

        int expected = GameResultCode.BAD_TURN_ORDER;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerReplaceStoneWhenHeHasStonesInHand(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);

        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 5 + " " + 7);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMill(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);

        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        int expected = GameState.WAIT_FOR_FIRST_PLAYER_DROP;
        int actual = mill.gameState;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMillAndThenHePutStone(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 8);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMillAndThenHeReplaceStone(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 4 + " " + 8);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMillAndDropHisOwnStone(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 4);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMillAndDropEmptyPosition(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 12);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMillAndDropOpponentStone(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 0);

        int expected = GameResultCode.OK;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeMillAndSecondPlayerTryToMakeTurn(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)

        mill.makeTurn(secondPlayer, Commands.DROP_STONE + 4);

        int expected = GameResultCode.BAD_TURN_ORDER;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenPlayerDropOpponentStoneInMillWhenThereAreOpponentStoneOutOfMill(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 14);    //out of millEngine (14)
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 2);    //secondPlayer create millEngine (0 1 2)
        mill.makeTurn(secondPlayer, Commands.DROP_STONE + 1);

        mill.makeTurn(secondPlayer, Commands.DROP_STONE + 4);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerDropOpponentStoneInMillWhenThereAreNotOpponentStoneOutOfMill(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);     //firstPlayer create millEngine (4 5 6)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 1);
        mill.gameState = GameState.WAIT_FOR_SECOND_PLAYER_TURN;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 2);    //secondPlayer create millEngine (0 1 2)
        mill.makeTurn(secondPlayer, Commands.DROP_STONE + 1);

        mill.makeTurn(secondPlayer, Commands.DROP_STONE + 4);

        int expected = GameResultCode.OK;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerPutStoneWhenHisHandEmpty(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 2);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 9);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 10);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 12);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 14);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 16);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 18);    // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 19);   // Doesn't have stone in hand

        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 20);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerMakeValidReplace(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 2);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 9);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 10);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 12);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 14);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 16);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 18);    // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 19);   // Doesn't have stone in hand

        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 0);

        int expected = GameResultCode.OK;
        int actual = mill.resultCode;
        assertEquals(expected, actual);

    }

    @Test
    public void WhenFirstPlayerReplaceStoneToNotFreePosition(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 2);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 9);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 10);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 12);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 14);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 16);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 18);    // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 19);   // Doesn't have stone in hand

        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 2);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerReplaceStoneToNotNeighborPositionWhenHeHasMoreThen3StoneInGame(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 2);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 6);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 9);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 10);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 12);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 14);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 16);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 18);    // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 19);   // Doesn't have stone in hand

        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 22);

        int expected = GameResultCode.BAD_TURN_LOGIC;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenSecondPlayerReplaceStoneToNotNeighborPositionWhenHeHasOly3StoneInGame(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 2);             //firstPlayer create millEngine (0 1 2)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 8 getStonesList;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);             //firstPlayer create millEngine (0 7 6)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 7 getStonesList;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 23);            //firstPlayer create millEngine (7 15 23)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 6 getStonesList;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 10);            // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 20);           // Doesn't have stone in hand
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 9);   //firstPlayer create millEngine (8 9 10)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 5 getStonesList;
        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 13 + " " + 5);
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 9 + " " + 1);   //firstPlayer create millEngine (0 1 2)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 4 getStonesList;
        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 4 + " " + 5);
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 9);   //firstPlayer create millEngine (8 9 10)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 5);                 //secondPlayer has 3 getStonesList;

        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 11 + " " + 5);

        int expected = GameResultCode.OK;
        int actual = mill.resultCode;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerReplaceHisStoneAndDestroyMillButCreateNewMill(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 2);             //firstPlayer create millEngine (0 1 2)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 23);
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 10);            // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 20);           // Doesn't have stone in hand

        //firstPlayer destroy millEngine (0 1 2) and create millEngine (8 9 10)
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 9);

        int expected = GameState.WAIT_FOR_FIRST_PLAYER_DROP;
        int actual = mill.gameState;
        assertEquals(expected, actual);
    }

    @Test
    public void WhenFirstPlayerDropOpponentStoneAndOpponentHas2Stones(){
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 0);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 4);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 1);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 2);             //firstPlayer create millEngine (0 1 2)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 8 getStonesList;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 7);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 3);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 6);             //firstPlayer create millEngine (0 7 6)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 7 getStonesList;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 15);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 11);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 23);            //firstPlayer create millEngine (7 15 23)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 6 getStonesList;
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 5);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 8);
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 13);
        mill.makeTurn(firstPlayer, Commands.PUT_NEW_STONE + 10);            // Doesn't have stone in hand
        mill.makeTurn(secondPlayer, Commands.PUT_NEW_STONE + 20);           // Doesn't have stone in hand
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 9);   //firstPlayer create millEngine (8 9 10)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 5 getStonesList;
        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 13 + " " + 5);
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 9 + " " + 1);   //firstPlayer create millEngine (0 1 2)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 4 getStonesList;
        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 4 + " " + 5);
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 1 + " " + 9);   //firstPlayer create millEngine (8 9 10)
        mill.makeTurn(firstPlayer, Commands.DROP_STONE + 5);                 //secondPlayer has 3 getStonesList;
        mill.makeTurn(secondPlayer, Commands.REPLACE_STONE + 11 + " " + 5);
        mill.makeTurn(firstPlayer, Commands.REPLACE_STONE + 9 + " " + 1);   //firstPlayer create millEngine (0 1 2)

        mill.makeTurn(firstPlayer, Commands.DROP_STONE +5);                 //secondPlayer has 2 getStonesList;

        int expected = GameState.FINISHED_WITH_FIRST_PLAYER_AS_A_WINNER;
        int actual = mill.gameState;
        assertEquals(expected, actual);
    }
}
