package com.mapia.domain.role;

import com.mapia.domain.Player;
import com.mapia.domain.User;

public abstract class Role {

    private RoleName roleName;
    
    public Role(RoleName roleName) {
    	this.roleName = roleName;
    }

    public abstract void vote(Player player);

    public String getRoleName() {
        return roleName.name();
    }

    @Override
    public String toString() {
        return roleName.name();
    }

	public boolean isMafia() {
		return roleName == RoleName.Mafia;
	}

    public boolean isDoctor() {
        return roleName == RoleName.Doctor;
    }
}
