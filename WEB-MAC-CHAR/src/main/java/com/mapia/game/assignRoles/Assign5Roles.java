package com.mapia.game.assignRoles;

import java.util.List;

import com.mapia.game.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign5Roles extends AssignRoleManager {
    public static final int MEMBER_COUNT = 5;
    private static final int MAFIA_COUNT = 1;
    private static final int CITIZEN_COUNT = 2;

    @Override
    public List<Role> customizedRole() {
        return super.makeRoles(MAFIA_COUNT, CITIZEN_COUNT, MEMBER_COUNT);
    }
}
