package com.mapia.domain;

import com.mapia.domain.role.Role;

public class Player {
	private User user;
	private Role role;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
