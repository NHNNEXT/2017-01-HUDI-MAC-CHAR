package com.zimincom.mafiaonline.item;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zimincom on 2017. 5. 7..
 */

public class Lobby implements Iterable {

    private final AtomicLong roomIdentifier = new AtomicLong();
    private volatile Map<Long, Room> rooms = new ConcurrentHashMap<>();

    public Collection<Room> rooms() {
        return rooms.values();
    }

    public Room getRoom(long id) {
        return rooms.get(id);
    }

    public long createRoom(String title) {
        long roomId = roomIdentifier.incrementAndGet();
        rooms.put(roomId, new Room(String.valueOf(roomId), title));
        return roomId;
    }

    public void delRoom(long id) {
        rooms.remove(id);
    }

    public boolean isExistRoom(long id) {
        return rooms.keySet().contains(id);
    }

    @Override
    public Iterator iterator() {
        return rooms.values().iterator();
    }

}
