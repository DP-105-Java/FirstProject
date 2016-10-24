package com.softserveinc.ita.multigame.model.managers;

import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.Game;
import com.softserveinc.ita.multigame.model.managers.impl.GameListManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class GameListManagerTest {
    private GameListManager manager;
    private static final Player player1 = new Player("1");
    private static final Player player2 = new Player("2");
    private static final Player player3 = new Player("3");

    private static Game firstGameCreatedByPlayer1;
    private static Game secondGameCreatedByPlayer2;
    private static Game firstGameCreatedByPlayer3;


    @Before
    public void setUp() {
        manager = GameListManager.getInstance();
    }

    @After
    public void tireDown() {
        try {
            Field field = manager.getClass().getDeclaredField("games");
            field.setAccessible(true);
            field.set(manager, new ConcurrentHashMap<>());
        } catch (Exception e) {
            fail("Field \"games\" is not accessible");
        }
    }

    @Test
    public void managerShouldBeSingleton() {
        GameListManager newManager = GameListManager.getInstance();
        assertSame(newManager, manager);
    }

    @Test
    public void newlyCreatedManagerShouldBeEmpty() {
        assertEquals(0, manager.getAllIDs().size());
    }

    @Test
    public void managerShouldContainAsManyGameAsHaveBeenCreated() {
        create4NewGames();

        assertEquals(4, manager.getAllIDs().size());
    }

    @Test
    public void countOfCreatedGamesByPlayer1MustBeOneAndGameCouldBeGetFromTheManagerAndPlayerShouldBePlayer1() {
        create4NewGames();

        List<Long> createdGameIDs = manager.getCreatedGameIDs(player1);

        assertEquals(1, createdGameIDs.size());
        assertSame(firstGameCreatedByPlayer1, manager.getGameById(createdGameIDs.get(0)));
        assertEquals(player1.getLogin(), firstGameCreatedByPlayer1.getFirstPlayerLogin());
    }

    @Test
    public void countOfCreatedGamesByPlayer2MustBeTwoAndSecondGameCouldBeGetFromTheManagerAndPlayerShouldBePlayer2() {
        create4NewGames();

        List<Long> createdGameIDs = manager.getCreatedGameIDs(player2);

        assertEquals(2, createdGameIDs.size());
        assertSame(secondGameCreatedByPlayer2, manager.getGameById(createdGameIDs.get(1)));
        assertEquals(player2.getLogin(), secondGameCreatedByPlayer2.getFirstPlayerLogin());
    }

    @Test
    public void Player2CouldJoinToGameCreatedByPlayer1(){
        create4NewGames();

        assertTrue(firstGameCreatedByPlayer1.joinToGame(player2));
        assertEquals(player1.getLogin(), firstGameCreatedByPlayer1.getFirstPlayerLogin());
        assertEquals(player2.getLogin(), firstGameCreatedByPlayer1.getSecondPlayerLogin());
    }

    @Test
    public void Player2CouldNotJoinToGameCreatedByPlayer3WhenPlayer1HasAlreadyJoinToThisGame(){
        create4NewGames();

        assertFalse(firstGameCreatedByPlayer3.joinToGame(player2));
    }

    @Test
    public void countOfPlayingGamesByPlayer1MustBeOneHisOpponentShouldBePlayer3() {
        create4NewGames();
        List<Long> playingGameIDs = manager.getPlayingGameIDs(player1);

        assertEquals(1, playingGameIDs.size());
        assertSame(firstGameCreatedByPlayer3, manager.getGameById(playingGameIDs.get(0)));
        assertEquals(player3.getLogin(), firstGameCreatedByPlayer3.getFirstPlayerLogin());
        assertEquals(player1.getLogin(), firstGameCreatedByPlayer3.getSecondPlayerLogin());
    }

    @Test
    public void countOfWaitingGamesForPlayer1MustBeTwoAndHisOpponentShouldBePlayer2AndHeCouldJoinToThisGame() {
        create4NewGames();

        List<Long> waitingGameIDs = manager.getWaitingGameIDs(player1);

        assertEquals(2, waitingGameIDs.size());
        assertSame(secondGameCreatedByPlayer2, manager.getGameById(waitingGameIDs.get(1)));
        assertEquals(player2.getLogin(), secondGameCreatedByPlayer2.getFirstPlayerLogin());
        assertTrue(secondGameCreatedByPlayer2.joinToGame(player1));
        assertEquals(player1.getLogin(), secondGameCreatedByPlayer2.getSecondPlayerLogin());
    }

    private void create4NewGames() {
        manager.createGame(player1);
        manager.createGame(player2);
        manager.createGame(player2);
        manager.createGame(player3);

        firstGameCreatedByPlayer1 = manager.getGameById(manager.getCreatedGameIDs(player1).get(0));
        secondGameCreatedByPlayer2 = manager.getGameById(manager.getCreatedGameIDs(player2).get(1));
        firstGameCreatedByPlayer3 = manager.getGameById(manager.getCreatedGameIDs(player3).get(0));
        firstGameCreatedByPlayer3.joinToGame(player1);

    }




}
