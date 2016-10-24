package com.softserveinc.ita.multigame.model.managers.impl;

import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.managers.PlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerListManager implements PlayerManager {
    private static Map<String, Player> players = new ConcurrentHashMap<>();

    private PlayerListManager(){
    }

    private static class SingletonHolder {
        static final PlayerListManager HOLDER_INSTANCE = new PlayerListManager();
    }

    public static PlayerListManager getInstance() {
        return PlayerListManager.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public boolean registerNewPlayer(Player player) {
        if (player != null && !players.containsKey(player.getLogin())){
            players.put(player.getLogin(), player);
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(String login) {
        return players.containsKey(login);
    }

    @Override
    public Player getPlayerByLogin(String login) {
        return players.get(login);
    }

    @Override
    public List<Player> getAll() {
        return new ArrayList<>(players.values());
    }

}
