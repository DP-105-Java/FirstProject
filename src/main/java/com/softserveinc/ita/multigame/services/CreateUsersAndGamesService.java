package com.softserveinc.ita.multigame.services;


import com.softserveinc.ita.multigame.model.Game;
import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.engine.millEngine.Commands;
import com.softserveinc.ita.multigame.model.managers.GameManager;
import com.softserveinc.ita.multigame.model.managers.PlayerManager;
import com.softserveinc.ita.multigame.model.managers.impl.GameListManager;
import com.softserveinc.ita.multigame.model.managers.impl.PlayerListManager;

public class CreateUsersAndGamesService {
    private static int count = 0;
    private static PlayerManager playerManager = PlayerListManager.getInstance();
    private static GameManager gameManager = GameListManager.getInstance();

    public static void create(){
        if (count != 0) return;

        playerManager.registerNewPlayer(new Player("Vasya", "Vasiliy"));
        playerManager.registerNewPlayer(new Player("Petya", "Petr"));
        playerManager.registerNewPlayer(new Player("Kolya", "Nikolay"));

        gameManager.createGame(playerManager.getPlayerByLogin("Vasya"));
        gameManager.createGame(playerManager.getPlayerByLogin("Petya"));
        gameManager.createGame(playerManager.getPlayerByLogin("Petya"));
        gameManager.createGame(playerManager.getPlayerByLogin("Kolya"));

        count++;

        playGame(playerManager.getPlayerByLogin("Vasya"), playerManager.getPlayerByLogin("Petya"));
    }

    private static void playGame(Player firstPlayer, Player secondPlayer) {
        Long id = gameManager.getCreatedGameIDs(firstPlayer).get(0);
        Game mill = gameManager.getGameById(id);
        mill.joinToGame(secondPlayer);

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
    }


}
