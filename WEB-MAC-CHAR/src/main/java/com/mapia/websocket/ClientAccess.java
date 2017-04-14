package com.mapia.websocket;

import java.util.Set;

import com.mapia.domain.User;

public class ClientAccess {
	private String userName;
	private String access;
	private Set<User> users;
	
	public ClientAccess() {}
	
	public ClientAccess(String userName, String access) {
		this.userName = userName;
		this.access = access;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
