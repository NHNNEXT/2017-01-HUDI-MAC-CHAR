package com.mapia.websocket;

public class GameStart {
	private String userName;

	public GameStart() {}
	
	public GameStart(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
