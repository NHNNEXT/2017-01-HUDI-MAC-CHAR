package com.mapia.domain.role;

import com.mapia.domain.Player;
import com.mapia.domain.User;

public abstract class Role {
	
	private RoleName roleName;
	
	public Player vote(Player player) {
		return player;
	}

	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}
}
