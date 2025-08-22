package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationService {

    List<Reservation> getAllReservations();

    List<Reservation> getUserReservations(UUID id);

    Reservation createReservation(Reservation reservation);

    Reservation updateReservation(UUID id, Reservation reservation);

    Optional<Reservation> getReservation(UUID id);

    void deleteReservation(UUID id);

    List<Reservation> getTeamReservations(UUID id);

    List<Reservation> getRoomReservations(UUID id);

    List<Reservation> getCurrentUserReservations();

// TODO:
//  maybe later implement these methods

//    List<Reservation> getReservationsByDate(LocalDateTime date);
//
//    List<Reservation> getUserReservationsByDate(LocalDateTime date, UUID id);
//
//    List<Reservation> getTeamReservationsByDate(LocalDateTime date, UUID id);
//
//    List<Reservation> getRoomReservationsByDate(LocalDateTime date,  UUID id);
//
//    List<Reservation> getTodayReservations();
//
//    List<Reservation> getTodayUserReservations(UUID id);
//
//    List<Reservation> getTodayTeamReservations(UUID id);
//
//    List<Reservation> getTodayRoomReservations(UUID id);
//
//    List<Reservation> getWeeklyReservations(LocalDateTime date);
//
//    List<Reservation> getWeeklyUserReservations(LocalDateTime date, UUID id);
//
//    List<Reservation> getWeeklyTeamReservations(LocalDateTime date);
//
//    List<Reservation> getWeeklyRoomReservations(LocalDateTime date);
//
//    List<Reservation> getMonthlyReservations(LocalDateTime date);
//
//    List<Reservation> getMonthlyUserReservations(LocalDateTime date);
//
//    List<Reservation> getMonthlyTeamReservations(LocalDateTime date);
//
//    List<Reservation> getMonthlyRoomReservations(LocalDateTime date);
}
