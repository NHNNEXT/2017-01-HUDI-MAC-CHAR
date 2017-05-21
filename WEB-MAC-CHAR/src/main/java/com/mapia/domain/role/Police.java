package com.mapia.domain.role;

import com.mapia.domain.Player;

public class Police extends Role {

    public Police() {
        super(RoleName.Police);
    }

    @Override
    public void vote(Player player) {
    }
}
