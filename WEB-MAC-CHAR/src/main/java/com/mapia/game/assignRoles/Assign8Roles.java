package com.mapia.game.assignRoles;

import java.util.List;

import com.mapia.game.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign8Roles extends AssignRoleManager {
    public static final int MEMBER_COUNT = 8;
    public static final int MAFIA_COUNT = 2;
    public static final int CITIZEN_COUNT = 4;

    @Override
    public List<Role> customizedRole() {
        return super.makeRoles(MAFIA_COUNT, CITIZEN_COUNT, MEMBER_COUNT);
    }
}
