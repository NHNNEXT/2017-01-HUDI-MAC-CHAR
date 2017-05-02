package com.mapia.domain.assignRoles;

import java.util.List;

import com.mapia.domain.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign8Roles extends AssignRoleManager{
	public static final int MEMBER_COUNT = 8;
	public final int MAFIA_COUNT = 2;
	public final int CITIZEN_COUNT = 4;

	@Override
	public void assign(List<Role> roles) {
		super.makeRoles(MAFIA_COUNT, CITIZEN_COUNT, roles);
	}
}
