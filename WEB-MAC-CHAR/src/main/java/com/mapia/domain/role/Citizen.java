package com.mapia.domain.role;

import com.mapia.domain.Player;

public class Citizen extends Role {

    public Citizen() {
        super(RoleName.Citizen);
    }

    @Override
    public void vote(Player player) {
    }
}
