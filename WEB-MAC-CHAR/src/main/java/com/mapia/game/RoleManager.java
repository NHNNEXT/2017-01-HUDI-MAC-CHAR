package com.mapia.game;

import com.mapia.domain.role.Role;
import com.mapia.game.assignRoles.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleManager {
    private static Map<Integer, AssignRoleManager> mappings = new HashMap<>();

    static {
        mappings.put(Assign2Roles.MEMBER_COUNT, new Assign2Roles());
        mappings.put(Assign4Roles.MEMBER_COUNT, new Assign4Roles());
        mappings.put(Assign5Roles.MEMBER_COUNT, new Assign5Roles());
        mappings.put(Assign6Roles.MEMBER_COUNT, new Assign6Roles());
        mappings.put(Assign7Roles.MEMBER_COUNT, new Assign7Roles());
        mappings.put(Assign8Roles.MEMBER_COUNT, new Assign8Roles());
    }

    public static List<Role> assignRoleToPlayers(GamePlayers players) {
        AssignRoleManager arm = mappings.get(players.countOfPlayers());
        if (arm == null) {
            throw new RuntimeException("Cannot find appropriate roles");
        }
        List<Role> roles = arm.customizedRole();
        Collections.shuffle(roles);
        players.setRole(roles);
        return roles;
    }
}
