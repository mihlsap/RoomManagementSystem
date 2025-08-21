package com.example.RoomManagementSystem.controllers;

import com.example.RoomManagementSystem.domain.entities.Room;
import com.example.RoomManagementSystem.services.RoomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('admin')")
@RequestMapping(path = "rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping( "all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("{room_id}")
    public Room getRoomById(@PathVariable("room_id") UUID id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @PutMapping("{room_id}")
    public Room updateRoom(@PathVariable("room_id") UUID id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @DeleteMapping("{room_id}")
    public void deleteRoom(@PathVariable("room_id") UUID id) {
        roomService.deleteRoom(id);
    }
}
