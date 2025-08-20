package com.example.RoomManagementSystem.repositories;

import com.example.RoomManagementSystem.domain.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
