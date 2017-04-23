package com.mapia.domain;

import java.util.HashMap;
import java.util.Map;

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
	void initMapping() {
		mappings.put(4, new Assign4Roles());
		mappings.put(5, new Assign5Roles());
		mappings.put(6, new Assign6Roles());
		mappings.put(7, new Assign7Roles());
		mappings.put(8, new Assign8Roles());
	}
	
	void put(int memberCount, AssignRoleManager assignRoleManager) {
		mappings.put(memberCount, assignRoleManager);
	}
	
	AssignRoleManager findAssignRoleManager(int memberCount) {
		return mappings.get(memberCount);
	}

}
