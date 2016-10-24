package com.softserveinc.ita.multigame.model.managers;

import com.softserveinc.ita.multigame.model.Player;

import java.util.List;

public interface PlayerManager {
    boolean registerNewPlayer(Player player);
    boolean contains(String login);
    Player getPlayerByLogin(String login);
    List<Player> getAll();

}
