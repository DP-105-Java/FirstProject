package com.softserveinc.ita.multigame.model.engine;

public class GameState {
    public static final int WAIT_FOR_FIRST_PLAYER = 1;
    public static final int WAIT_FOR_SECOND_PLAYER = 2;
    public static final int WAIT_FOR_FIRST_PLAYER_TURN = 3;
    public static final int WAIT_FOR_SECOND_PLAYER_TURN = 4;
    
    public static final int CREATED = WAIT_FOR_FIRST_PLAYER;
    public static final int FINISHED_WITH_FIRST_PLAYER_AS_A_WINNER = 5;
    public static final int FINISHED_WITH_SECOND_PLAYER_AS_A_WINNER = 6;
    public static final int FINISHED_WITH_DRAW = 7;
    
    public static final int UNDEFINED = -1;
    
    public static final int EXTRA_STATE = 100;
    
    //Add your states in format:
    //YOUR_STATE_1 = EXTRA_STATE + 1;
    //YOUR_STATE_2 = EXTRA_STATE + 2;
    //etc.

	public static final int WAIT_FOR_FIRST_PLAYER_DROP = EXTRA_STATE + 1;
    public static final int WAIT_FOR_SECOND_PLAYER_DROP = EXTRA_STATE + 2;

}
