package com.example.RoomManagementSystem.repositories;

import com.example.RoomManagementSystem.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
