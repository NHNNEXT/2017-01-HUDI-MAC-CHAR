package com.mapia.domain.assignRoles;

import java.util.List;

import com.mapia.domain.AssignRoleManager;
import com.mapia.domain.role.Role;

public class Assign8Roles extends AssignRoleManager{

	@Override
	public void assign(List<Role> roles) {
		super.makeRoles(2, 4, roles);
	}
}
