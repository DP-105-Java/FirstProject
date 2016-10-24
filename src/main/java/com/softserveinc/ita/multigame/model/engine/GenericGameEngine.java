package com.softserveinc.ita.multigame.model.engine;

import java.util.Map;

//Test using Mockito as 
//GameEngine ge = Mockito.mock(GenericGameEngine.class, Mockito.CALLS_REAL_METHODS);
//then invoke all implemented methods on ge
public abstract class GenericGameEngine<T> implements GameEngine<T> {
    protected T firstPlayer;
    protected T secondPlayer;

    protected int gameState = GameState.CREATED;
    protected int resultCode = GameResultCode.OK;

    private final Long id;
    private T theWinner;
    private boolean isStarted = false;

    private static Long nextId = 0L;

    public GenericGameEngine() {
	this(nextId++);
	if (nextId == Long.MAX_VALUE) {
	    throw new RuntimeException(
		    String.format("%s has reached maximum ID value. " + 
			    "Restart the Application.",
		    this.getClass().getSimpleName()));
	}
    }

    // For overriding in a Subclass
    protected GenericGameEngine(final Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    abstract protected boolean validateTurnSyntax(String turn);

    abstract protected boolean validateTurnLogic(String turn);

    abstract protected boolean validatePlayer(T player);

    abstract protected int changeGameState(T player, String turn);

    protected boolean validateTurnOrder(T player) {
	boolean firstPlayerCorrectOrder = gameState == GameState.WAIT_FOR_FIRST_PLAYER_TURN
		&& firstPlayer.equals(player);
	boolean secondPlayerCorrectOrder = gameState == GameState.WAIT_FOR_SECOND_PLAYER_TURN
		&& secondPlayer.equals(player);
	return firstPlayerCorrectOrder || secondPlayerCorrectOrder;
    }

    @Override
    public boolean setFirstPlayer(T player) {
	if (gameState != GameState.WAIT_FOR_FIRST_PLAYER) {
	    return errorWith(GameResultCode.BAD_FIRST_PLAYER_ORDER);
	}
	if (!validatePlayer(player)) {
	    return errorWith(GameResultCode.BAD_PLAYER);
	}
	firstPlayer = player;
	gameState = GameState.WAIT_FOR_SECOND_PLAYER;
	return ok();
    }

    @Override
    public T getFirstPlayer() {
	return firstPlayer;
    }

    @Override
    public boolean setSecondPlayer(T player) {
	if (gameState != GameState.WAIT_FOR_SECOND_PLAYER) {
	    return errorWith(GameResultCode.BAD_SECOND_PLAYER_ORDER);
	}
	if (!validatePlayer(player)) {
	    return errorWith(GameResultCode.BAD_PLAYER);
	}
	if (player.equals(firstPlayer)) {
	    return errorWith(GameResultCode.BAD_FIRST_PLAYER_ORDER);
	}
	secondPlayer = player;
	gameState = GameState.WAIT_FOR_FIRST_PLAYER_TURN;
	isStarted = true;
	return ok();
    }

    @Override
    public T getSecondPlayer() {
	return secondPlayer;
    }

    @Override
    public String getBoard() {
	throw new UnsupportedOperationException(
		String.format("%s does not use any board", this.getClass().getSimpleName()));
    }

    @Override
    public Map<String, Object> getInfo() {
	throw new UnsupportedOperationException(
		String.format("%s does not provide any information", this.getClass().getSimpleName()));
    }

    @Override
    public boolean init(T player, Map<String, Object> initData) {
	throw new UnsupportedOperationException(
		String.format("%s does not support initialization", this.getClass().getSimpleName()));
    }

    @Override
    public boolean makeTurn(T player, String turn) {
	if (!isStarted()) {
	    return errorWith(GameResultCode.BAD_TURN_FOR_NOT_STARTED_GAME);
	}
	if (isFinished()) {
	    return errorWith(GameResultCode.BAD_TURN_FOR_FINISHED_GAME);
	}
	if (!validatePlayer(player)) {
	    return errorWith(GameResultCode.BAD_PLAYER);
	}
	if (!validateTurnOrder(player)) {
	    return errorWith(GameResultCode.BAD_TURN_ORDER);
	}
	if (!validateTurnSyntax(turn)) {
	    return errorWith(GameResultCode.BAD_TURN_SYNTAX);
	}
	if (!validateTurnLogic(turn)) {
	    return errorWith(GameResultCode.BAD_TURN_LOGIC);
	}

	gameState = changeGameState(player, turn);

	if (gameState == GameState.FINISHED_WITH_FIRST_PLAYER_AS_A_WINNER) {
	    theWinner = firstPlayer;
	}
	if (gameState == GameState.FINISHED_WITH_SECOND_PLAYER_AS_A_WINNER) {
	    theWinner = secondPlayer;
	}
	if (gameState == GameState.FINISHED_WITH_DRAW) {
	    theWinner = null;
	}
	return ok();
    }

    @Override
    public int getResultCode() {
	return resultCode;
    }

    @Override
    public boolean isFinished() {
	return gameState == GameState.FINISHED_WITH_FIRST_PLAYER_AS_A_WINNER
		|| gameState == GameState.FINISHED_WITH_SECOND_PLAYER_AS_A_WINNER
		|| gameState == GameState.FINISHED_WITH_DRAW;
    }

    @Override
    public boolean isStarted() {
	return isStarted;
    }

    @Override
    public T getTheWinner() {
	return theWinner;
    }

    @Override
    public String toString() {
	return String.format("%s #%s [ %s vs %s ]: state=%s, code=%s, winner=%s", this.getClass().getSimpleName(), id,
		firstPlayer, secondPlayer, gameState, resultCode, theWinner);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	@SuppressWarnings("unchecked")
	GenericGameEngine<T> other = (GenericGameEngine<T>) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    private boolean errorWith(int code) {
	resultCode = code;
	return false;
    }

    private boolean ok() {
	resultCode = GameResultCode.OK;
	return true;
    }

}
