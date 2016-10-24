package com.softserveinc.ita.multigame.model.managers.impl;

import com.softserveinc.ita.multigame.model.Game;
import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.managers.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameListManager implements GameManager {
    private  Map<Long, Game> games = new ConcurrentHashMap<>();

    private GameListManager() {
    }

    private static class SingletonHolder {
        static final GameListManager HOLDER_INSTANCE = new GameListManager();
    }

    public static GameListManager getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }



    @Override
    public boolean createGame(Player player) {
        if (player != null){
            Game game = new Game(player);
            games.put(game.getId(), game);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteGame(long id) {
        return true;
    }

    @Override
    public Game getGameById(long id) {
        return games.get(id);
    }

    @Override
    public List<Long> getCreatedGameIDs(Player player) {
        List<Long> ids = new ArrayList<>();

        for (Long id : games.keySet()){

            Game game = games.get(id);
            if (!game.isFinished()) {
                if (player.getLogin().equals(game.getFirstPlayerLogin())
                        && game.getSecondPlayerLogin() == null) {
                    ids.add(id);
                }
            }
        }
        return ids;
    }


    @Override
    public List<Long> getPlayingGameIDs(Player player) {
        List<Long> ids = new ArrayList<>();

        for (Long id : games.keySet()){
            Game game = games.get(id);
            if (!game.isFinished()) {
                if (player.getLogin().equals(game.getFirstPlayerLogin())
                        && game.getSecondPlayerLogin() != null) {
                    ids.add(id);
                } else if (player.getLogin().equals(game.getSecondPlayerLogin())
                        && game.getFirstPlayerLogin() != null) {
                    ids.add(id);
                }
            }
        }
        return ids;
    }

    @Override
    public List<Long> getWaitingGameIDs(Player player) {
        List<Long> ids = new ArrayList<>();

        for (Long id : games.keySet()){
            Game game = games.get(id);
            if (!game.isFinished()) {
                if (!player.getLogin().equals(game.getFirstPlayerLogin())
                        && game.getSecondPlayerLogin() == null) {
                    ids.add(id);
                }
            }
        }
        return ids;
    }

    @Override
    public List<Long> getAllIDs() {
        return new ArrayList<>(games.keySet());
    }

}
