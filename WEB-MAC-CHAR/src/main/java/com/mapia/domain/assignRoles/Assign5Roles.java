package com.mapia.domain.assignRoles;

import java.util.List;

import com.mapia.domain.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign5Roles extends AssignRoleManager{
	public static final int MEMBER_COUNT = 5;
	private final int MAFIA_COUNT = 1;
	private final int CITIZEN_COUNT = 2;
	
	@Override
	public void assign(List<Role> roles) {
		super.makeRoles(MAFIA_COUNT, CITIZEN_COUNT, roles);
	}
}
