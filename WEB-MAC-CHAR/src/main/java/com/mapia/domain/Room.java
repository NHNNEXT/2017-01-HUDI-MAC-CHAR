package com.mapia.domain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Room {
	private static final int CAPACITY = 8;
	private Set<User> users = Collections.synchronizedSet(new LinkedHashSet<>(CAPACITY));
	private long id;
	private String title;
	private volatile int userCount;
	private boolean secretMode;

	public Room(long roomId, String title) {
		this.id = roomId;
		this.title = title;
	}

	public Set<User> getUsers() {
		return users;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getUserCount() {
		return getCountOfUserInRoom();
	}

	public boolean isSecretMode() {
		return secretMode;
	}

	public void setSecretMode() {
		this.secretMode = true;
	}

	private int getCountOfUserInRoom() {
		return users.size();
	}

	public boolean isFull() {
		return getCountOfUserInRoom() >= CAPACITY;
	}
	
	public boolean isEmpty() {
		return getCountOfUserInRoom() == 0;
	}

    @Override
    public String toString() {
        return "Room{" +
                "users=" + users +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", userCount=" + userCount +
                '}';
    }

	public void enter(User user) {
		user.enterRoom(this.id);
		users.add(user);
	}

	public void exit(User user) {
		user.enterLobby();
		users.remove(user);
	}
}
