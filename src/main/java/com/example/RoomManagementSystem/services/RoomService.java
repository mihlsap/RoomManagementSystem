package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Room;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    List<Room> getAllRooms();

    Room getRoomById(UUID id);

    Room addRoom(Room room);

    Room updateRoom(UUID id, Room room);

    void deleteRoom(UUID id);
}
