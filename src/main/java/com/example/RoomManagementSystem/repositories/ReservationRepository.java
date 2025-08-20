package com.example.RoomManagementSystem.repositories;

import com.example.RoomManagementSystem.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByOwnerId(UUID id);

    List<Reservation> findByRoomId(UUID id);

    List<Reservation> findByTeamId(UUID id);
}
