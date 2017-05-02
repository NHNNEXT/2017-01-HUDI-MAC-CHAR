package com.mapia.domain.role;

import com.mapia.domain.Player;

public class Police extends Role{
	
	public Police() {
		super.setRoleName(RoleName.Police);
	}
	
	public	Player investigation(Player player) {
		return player;
	}
}
