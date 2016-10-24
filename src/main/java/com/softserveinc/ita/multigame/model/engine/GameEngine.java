package com.softserveinc.ita.multigame.model.engine;

import java.util.Map;

public interface GameEngine<T> {
    Long getId();
    boolean setFirstPlayer(T playerId);
    T getFirstPlayer();
    boolean setSecondPlayer(T playerId);
    T getSecondPlayer();
    boolean makeTurn(T playerId, String turn);
    int getResultCode();
    boolean isFinished();
    boolean isStarted();  //After the MillPlayer names are set correctly
    Object getBoard();
    T getTheWinner();
    boolean init(T playerId, Map<String, Object> initData);
    Map<String, Object> getInfo();
}
