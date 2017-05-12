package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.role.Role;
import com.mapia.game.assignRoles.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        List<Role> roles = arm.customizedRole();
        Collections.shuffle(roles);
        players.setRole(roles);
        return roles;
    }
}
