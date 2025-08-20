package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationService {

    List<Reservation> getAllReservations();

    List<Reservation> getUserReservations(UUID id);

    Reservation createReservation(Reservation reservation);

    Reservation updateReservation(UUID userId, UUID id, Reservation reservation);

    Optional<Reservation> getReservation(UUID id);

    void deleteReservation(UUID userId, UUID id);

    List<Reservation> getTeamReservations(UUID id);

    List<Reservation> getRoomReservations(UUID id);
}
