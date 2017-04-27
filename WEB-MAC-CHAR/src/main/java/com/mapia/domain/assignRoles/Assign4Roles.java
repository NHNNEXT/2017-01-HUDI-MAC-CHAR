package com.mapia.domain.assignRoles;

import java.util.List;

import com.mapia.domain.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign4Roles extends AssignRoleManager{
	public static final int MEMBER_COUNT = 4;
	public final int MAFIA_COUNT = 1;
	public final int CITIZEN_COUNT = 1;
	
	@Override
	public void assign(List<Role> roles) {
		super.makeRoles(1, 1, roles);
	}
}
