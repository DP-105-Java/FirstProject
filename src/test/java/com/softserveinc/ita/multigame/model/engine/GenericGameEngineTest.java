package com.softserveinc.ita.multigame.model.engine;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class GenericGameEngineTest {

    private final String thePlayer = "first";
    private final String theSamePlayer = thePlayer;
    private final String anotherPlayer = "second";

    private GameEngine<String> engine;

    @Test
    public void firstPlayerCouldBeSetAsAFirstOne() {
	assertTrue(engine.setFirstPlayer(thePlayer));
    }

    @Test
    public void firstPlayerCouldNotBeSetAsASecondOne() {
	assertTrue(engine.setFirstPlayer(thePlayer));
	assertFalse(engine.setSecondPlayer(theSamePlayer));
	assertTrue(engine.getResultCode() == GameResultCode.BAD_FIRST_PLAYER_ORDER);
    }

    @Test
    public void theFirstPlayerCouldNotBeSetTwice() {
	assertTrue(engine.setFirstPlayer(thePlayer));
	assertFalse(engine.setFirstPlayer(theSamePlayer));
	assertTrue(engine.getResultCode() == GameResultCode.BAD_FIRST_PLAYER_ORDER);
    }

    @Test
    public void theSecondPlayerCouldNotBeSetTwice() {
	assertTrue(engine.setFirstPlayer(anotherPlayer));
	assertTrue(engine.setSecondPlayer(thePlayer));
	assertFalse(engine.setFirstPlayer(theSamePlayer));
	assertTrue(engine.getResultCode() == GameResultCode.BAD_FIRST_PLAYER_ORDER);
    }

    @Test
    public void twoPlayersCouldBeSetOneByOne() {
	assertTrue(engine.setFirstPlayer(thePlayer));
	assertTrue(engine.setSecondPlayer(anotherPlayer));
	assertTrue(engine.getResultCode() == GameResultCode.OK);
    }

    @Test
    public void afterTheSecondPlayerIsSetTheGameShouldBeStarted() {
	assertTrue(engine.setFirstPlayer(thePlayer));
	assertTrue(engine.setSecondPlayer(anotherPlayer));
	assertTrue(engine.isStarted());
    }

    @Test
    public void createdGameShouldNotBeFinished() {
	assertFalse(engine.isFinished());
    }


    @Test
    public void firstAndSecondAndThridGameEnginesSouldHaveIDs0and1and2() {
	setGameEngineNextId(0L);
	
	GameEngine<String> firstEngine = new EmptyGameEngine();
	GameEngine<String> secondEngine = new EmptyGameEngine();
	GameEngine<String> thirdEngine = new EmptyGameEngine();
	
	assertEquals((Long) 0L, (Long) firstEngine.getId());
	assertEquals((Long) 1L, (Long) secondEngine.getId());
	assertEquals((Long) 2L, (Long) thirdEngine.getId());
    }
    
    @Test(expected = RuntimeException.class)
    public void whenMaxGameIdValueIsReachedExceptionShouldBeThrown() {
	setGameEngineNextId(Long.MAX_VALUE - 1);
	new EmptyGameEngine();
    }
    

    @Before
    public void setUp() {
	engine = new EmptyGameEngine();
    }

    private class EmptyGameEngine extends GenericGameEngine<String> {

	@Override
	protected boolean validateTurnSyntax(String turn) {
	    return true;
	}

	@Override
	protected boolean validateTurnLogic(String turn) {
	    return true;
	}

	@Override
	protected boolean validatePlayer(String player) {
	    return true;
	}

	@Override
	protected int changeGameState(String player, String turn) {
	    return GameState.UNDEFINED;
	}
    }

    private static void setGameEngineNextId(Long newValue) {
	try {
	    Field field = GenericGameEngine.class.getDeclaredField("nextId");
	    field.setAccessible(true);
	    field.set(null, newValue);
	} catch (Exception e) {
	    fail("Field \"nextId\" is not accessible");
	}
    }

}
