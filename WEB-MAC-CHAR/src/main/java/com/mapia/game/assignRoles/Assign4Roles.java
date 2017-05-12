package com.mapia.game.assignRoles;

import java.util.List;

import com.mapia.game.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign4Roles extends AssignRoleManager {
    public static final int MEMBER_COUNT = 4;
    public static final int MAFIA_COUNT = 1;
    public static final int CITIZEN_COUNT = 1;

    @Override
    public List<Role> customizedRole() {
        return super.makeRoles(MAFIA_COUNT, CITIZEN_COUNT, MEMBER_COUNT);
    }
}
