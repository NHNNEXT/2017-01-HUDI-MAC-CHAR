package com.mapia.domain.role;

import com.mapia.domain.Player;
import com.mapia.domain.User;

public abstract class Role {

    private RoleName roleName;

    public Player vote(Player player) {
        return player;
    }

    public String getRoleName() {
        return roleName.name();
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName.name();
    }
}
