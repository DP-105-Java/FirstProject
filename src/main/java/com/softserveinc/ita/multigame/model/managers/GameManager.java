package com.softserveinc.ita.multigame.model.managers;

import com.softserveinc.ita.multigame.model.Player;
import com.softserveinc.ita.multigame.model.Game;

import java.util.List;

public interface GameManager {
    boolean createGame(Player player);
    boolean deleteGame(long id);
    Game getGameById(long id);
    List<Long> getCreatedGameIDs(Player player);
    List<Long> getPlayingGameIDs(Player player);
    List<Long> getWaitingGameIDs(Player player);
    List<Long> getAllIDs();


}
