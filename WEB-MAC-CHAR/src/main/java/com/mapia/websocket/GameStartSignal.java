package com.mapia.websocket;

public class GameStartSignal {
	private boolean gameStart;

	public GameStartSignal(boolean gameStart) {
		this.gameStart = gameStart;
	}

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}
	
}
