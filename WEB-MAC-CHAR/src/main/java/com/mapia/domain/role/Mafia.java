package com.mapia.domain.role;

import com.mapia.domain.Player;

public class Mafia extends Role {

    public Mafia() {
        super(RoleName.Mafia);
    }

    @Override
    public void vote(Player player) {
        player.killed(false);
    }
}
