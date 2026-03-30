package com.dsantos.repository;

import com.dsantos.model.Room;

import java.util.*;

public class InMemoryRoomRepository implements RoomRepository {

    private final Map<String, Room> store = new HashMap<>();

    @Override
    public void save(Room room) {
        store.put(room.getId(), room);
    }

    @Override
    public Optional<Room> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }

    @Override
    public int count() {
        return store.size();
    }

    @Override
    public List<Room> findByMinCapacity(int minCapacity) {
        List<Room> result = new ArrayList<>();
        for (Room room : store.values()) {
            if (room.getCapacity() >= minCapacity) {
                result.add(room);
            }
        }
        return result;
    }
}

