package com.mapia.websocket;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapia.domain.Lobby;

public class ClientReady {
	private String userName;
	
	@Autowired
	Lobby lobby;
	
	public ClientReady() {}
	
	public ClientReady(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
