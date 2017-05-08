package com.mapia.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.role.Citizen;
import com.mapia.domain.role.Doctor;
import com.mapia.domain.role.Mafia;
import com.mapia.domain.role.Police;
import com.mapia.domain.role.Role;

public class GameManager {
	private static final Logger log = LoggerFactory.getLogger(GameManager.class);
	
	private List<Role> roles = new ArrayList<>();
	private List<Player> players = new ArrayList<>();

	public void assignRole() {
		int playerCount = players.size();
		
		AssignRoleManagerMapping am = new AssignRoleManagerMapping();
		AssignRoleManager assignRoleManager = am.findAssignRoleManager(playerCount);
		log.debug("{}", playerCount);
		assignRoleManager.assign(roles);
		
		shuffleRoles();
		for( int i = 0 ; i < playerCount ; i++) {
			players.get(i).setRole(roles.get(i));
		}
		log.debug("{}", players.toString());
	}
	
	private void shuffleRoles() {
		long seed = System.nanoTime();
		Collections.shuffle(roles, new Random(seed));
		log.debug("ROLES AFTER SHUFFLE: {}", roles.toString());
	}
	
	public void setPlayers(List<Player> players) {
		players = players;
	}
	
	public void addPlayer(User user) {
		players.add(new Player(user));
	}
	
	public Role findRoleNameByUserName(String userName) {
		for (Player player : players) {
			log.info("PLAYER NIACKNAME: {}", player.getUser().getNickname());
			log.info("USER NAME: {}", userName);
			if(player.getUser().getNickname().contains(userName)) {
				return player.getRole();
			}
		}
		return null;
		
	}
}
