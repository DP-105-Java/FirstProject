package com.softserveinc.ita.multigame.model.engine;

public class GameResultCode {
    public static final int OK = 0;

    
    public static final int BAD_PLAYER = 1;
    public static final int BAD_FIRST_PLAYER_ORDER = 2;
    public static final int BAD_SECOND_PLAYER_ORDER = 3;
    public static final int BAD_TURN_ORDER = 4;
    public static final int BAD_TURN_SYNTAX = 5;
    public static final int BAD_TURN_LOGIC = 6;
    public static final int BAD_TURN_FOR_FINISHED_GAME = 7;
    public static final int BAD_TURN_FOR_NOT_STARTED_GAME = 8;
    
    public static final int EXTRA_CODE = 100;
    
    //Add your result codes in format:
    //YOUR_CODE_1 = EXTRA_CODE + 1;
    //YOUR_CODE_2 = EXTRA_CODE + 2;
    //etc.
}
