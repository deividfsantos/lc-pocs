package com.dsantos.repository;

import com.dsantos.model.Room;

import java.util.List;

public interface RoomRepository extends Repository<Room, String> {
    List<Room> findByMinCapacity(int minCapacity);
}

