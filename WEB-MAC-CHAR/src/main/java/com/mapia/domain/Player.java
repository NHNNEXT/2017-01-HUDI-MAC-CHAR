package com.mapia.domain;

import com.mapia.domain.role.Role;

public class Player {
	private User user;
	private Role role;
	private boolean stillAlive = true;
	
	public Player(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
