package com.mapia.domain.role;

import com.mapia.domain.Player;

public class Doctor extends Role {

    public Doctor() {
        super(RoleName.Doctor);
    }

    @Override
    public void vote(Player player) {
    }
}
