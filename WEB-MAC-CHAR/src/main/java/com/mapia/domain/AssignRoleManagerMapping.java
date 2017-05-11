package com.mapia.domain;

import java.util.HashMap;
import java.util.Map;

import com.mapia.domain.assignRoles.Assign2Roles;
import com.mapia.domain.assignRoles.Assign4Roles;
import com.mapia.domain.assignRoles.Assign5Roles;
import com.mapia.domain.assignRoles.Assign6Roles;
import com.mapia.domain.assignRoles.Assign7Roles;
import com.mapia.domain.assignRoles.Assign8Roles;

public class AssignRoleManagerMapping {
	private Map<Integer, AssignRoleManager> mappings = new HashMap<>();
	
	public AssignRoleManagerMapping() {
		initMapping();
	}
	private void initMapping() {
		mappings.put(Assign2Roles.MEMBER_COUNT, new Assign2Roles());
		mappings.put(Assign4Roles.MEMBER_COUNT, new Assign4Roles());
		mappings.put(Assign5Roles.MEMBER_COUNT, new Assign5Roles());
		mappings.put(Assign6Roles.MEMBER_COUNT, new Assign6Roles());
		mappings.put(Assign7Roles.MEMBER_COUNT, new Assign7Roles());
		mappings.put(Assign8Roles.MEMBER_COUNT, new Assign8Roles());
	}
	
	AssignRoleManager findAssignRoleManager(int memberCount) {
		return mappings.get(memberCount);
	}

}
