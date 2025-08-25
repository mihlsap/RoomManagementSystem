package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Room;
import com.example.RoomManagementSystem.domain.pagination.PaginationRequest;
import com.example.RoomManagementSystem.domain.pagination.PagingResult;

import java.util.UUID;

public interface RoomService {

    PagingResult<Room> getAllRooms(PaginationRequest paginationRequest);

    Room getRoomById(UUID id);

    Room addRoom(Room room);

    Room updateRoom(UUID id, Room room);

    void deleteRoom(UUID id);
}
