package com.mapia.domain.role;

import com.mapia.domain.Player;

public class Doctor extends Role {

    public Doctor() {
        super.setRoleName(RoleName.Doctor);
    }

    public Player heal(Player player) {
        return player;
    }

}
