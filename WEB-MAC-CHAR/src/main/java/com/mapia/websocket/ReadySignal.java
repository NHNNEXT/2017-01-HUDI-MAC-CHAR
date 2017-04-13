package com.mapia.websocket;

public class ReadySignal {
	private String userName;
	
	public ReadySignal() {}

	public ReadySignal(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
