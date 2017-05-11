package com.mapia.game.assignRoles;

import java.util.List;

import com.mapia.game.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign6Roles extends AssignRoleManager{
	public static final int MEMBER_COUNT = 6;
	public final int MAFIA_COUNT = 2;
	public final int CITIZEN_COUNT = 2;

    @Override
    public List<Role> customizedRole() {
        return super.makeRoles(MAFIA_COUNT, CITIZEN_COUNT);
    }
}
