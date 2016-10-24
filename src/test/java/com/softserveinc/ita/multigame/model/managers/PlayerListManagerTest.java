package com.softserveinc.ita.multigame.model.managers;


import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.managers.impl.PlayerListManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class PlayerListManagerTest {
    private PlayerListManager manager;
    private static final Player player1 = new Player("1");
    private static final Player player2 = new Player("2");
    private static final Player player3 = new Player("3");

    @Before
    public void setUp() {
        manager = PlayerListManager.getInstance();
    }

    @After
    public void tireDown() {
        try {
            Field field = manager.getClass().getDeclaredField("players");
            field.setAccessible(true);
            field.set(manager, new ConcurrentHashMap<>());
        } catch (Exception e) {
            fail("Field \"players\" is not accessible");
        }
    }

    @Test
    public void newlyCreatedManagerShouldBeEmpty() {
        assertEquals(0, manager.getAll().size());
    }

    @Test
    public void managerShouldContainAsManyPlayerAsHaveBeenAdded() {
        register3NewPlayers();
        assertEquals(3, manager.getAll().size());
    }

    @Test
    public void managerShouldBeSingleton() {
        PlayerListManager newManager = PlayerListManager.getInstance();
        assertSame(newManager, manager);
    }


    @Test
    public void firstRegisteredPlayerCouldBeGetFromTheManager() {
        register3NewPlayers();
        Player player = manager.getPlayerByLogin(player1.getLogin());
        assertEquals(player1, player);
    }

    @Test
    public void lastRegisteredPlayerCouldBeGetFromTheManager() {
        register3NewPlayers();
        Player player = manager.getPlayerByLogin(player3.getLogin());
        assertEquals(player3, player);
    }

    @Test
    public void playerShouldNotBeSavedTwice() {
        register3NewPlayers();
        assertFalse(manager.registerNewPlayer(player1));
        assertEquals(3, manager.getAll().size());
    }

    private void register3NewPlayers() {
        manager.registerNewPlayer(player1);
        manager.registerNewPlayer(player2);
        manager.registerNewPlayer(player3);
    }
}
