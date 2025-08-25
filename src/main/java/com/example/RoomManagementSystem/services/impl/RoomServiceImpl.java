package com.example.RoomManagementSystem.services.impl;

import com.example.RoomManagementSystem.domain.entities.Room;
import com.example.RoomManagementSystem.domain.pagination.PaginationRequest;
import com.example.RoomManagementSystem.domain.pagination.PaginationUtils;
import com.example.RoomManagementSystem.domain.pagination.PagingResult;
import com.example.RoomManagementSystem.repositories.RoomRepository;
import com.example.RoomManagementSystem.services.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public PagingResult<Room> getAllRooms(PaginationRequest paginationRequest) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequest);
        final Page<Room> rooms = roomRepository.findAll(pageable);
        return new PagingResult<>(
                rooms.getContent(),
                rooms.getTotalPages(),
                rooms.getTotalElements(),
                rooms.getSize(),
                rooms.getNumber(),
                rooms.isEmpty()
        );
    }

    @Override
    public Room getRoomById(UUID id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public Room addRoom(Room room) {

        validateInput(room);

        return roomRepository.save(new Room(
                null,
                room.getNumber(),
                room.getFloor(),
                room.getSize(),
                room.getSeats()
        ));
    }

    @Transactional
    @Override
    public Room updateRoom(UUID id, Room room) {

        Room existingRoom = roomRepository.findById(id).orElse(null);

        if (existingRoom == null)
            throw new IllegalArgumentException("Room does not exist!");

        if (room.getNumber() != null && !room.getNumber().equals(existingRoom.getNumber()))
            existingRoom.setNumber(room.getNumber());
        if (room.getSize() != null && !room.getSize().equals(existingRoom.getSize()))
            existingRoom.setSize(room.getSize());
        if (room.getSeats() != null && !room.getSeats().equals(existingRoom.getSeats()))
            existingRoom.setSeats(room.getSeats());

        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    private void validateInput(Room room) {

        if (Objects.isNull(room))
            throw new IllegalArgumentException("Room cannot be null!");

        if (room.getId() != null)
            throw new IllegalArgumentException("Room already has an ID!");

        if (room.getNumber() == null)
            throw new IllegalArgumentException("Room number cannot be null!");

        if (room.getFloor() == null)
            throw new IllegalArgumentException("Room floor cannot be null!");

        if (room.getSeats() == null)
            throw new IllegalArgumentException("Number of seats cannot be null!");
    }
}
