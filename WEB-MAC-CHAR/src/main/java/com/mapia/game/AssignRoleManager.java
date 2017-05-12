package com.mapia.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.role.Citizen;
import com.mapia.domain.role.Doctor;
import com.mapia.domain.role.Mafia;
import com.mapia.domain.role.Police;
import com.mapia.domain.role.Role;

public abstract class AssignRoleManager {
    private static final Logger log = LoggerFactory.getLogger(AssignRoleManager.class);

    public List<Role> makeRoles(int mafiaCount, int citizenCount, int memberCount) {
        List<Role> roles = new ArrayList<>(memberCount);
        for (int i = 0; i < mafiaCount; i++) {
            roles.add(new Mafia());
        }

        for (int i = 0; i < citizenCount; i++) {
            roles.add(new Citizen());
        }

        roles.add(new Police());
        roles.add(new Doctor());
        log.debug("ROLES: {}", roles);
        return roles;
    }

    public abstract List<Role> customizedRole();
}
