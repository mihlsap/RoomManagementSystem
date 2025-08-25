package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Reservation;

import java.time.LocalDate;
import java.time.YearMonth;
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

    List<Reservation> getReservationsByDate(LocalDate date);

    List<Reservation> getCurrentUserReservationsByDate(LocalDate date);

    List<Reservation> getUserReservationsByDate(LocalDate date, UUID id);

    List<Reservation> getTeamReservationsByDate(LocalDate date, UUID id);

    List<Reservation> getRoomReservationsByDate(LocalDate date, UUID id);

    List<Reservation> getTodayReservations();

    List<Reservation> getTodayUserReservations(UUID id);

    List<Reservation> getTodayCurrentUserReservations();

    List<Reservation> getTodayTeamReservations(UUID id);

    List<Reservation> getTodayRoomReservations(UUID id);

    List<Reservation> getReservationsByWeek(int year, int week);

    List<Reservation> getUserReservationsByWeek(int year, int week, UUID id);

    List<Reservation> getCurrentUserReservationsByWeek(int year, int week);

    List<Reservation> getTeamReservationsByWeek(int year, int week, UUID id);

    List<Reservation> getRoomReservationsByWeek(int year, int week, UUID id);

    List<Reservation> getReservationsByMonth(YearMonth yearMonth);

    List<Reservation> getUserReservationsByMonth(YearMonth yearMonth, UUID id);

    List<Reservation> getCurrentUserReservationsByMonth(YearMonth yearMonth);

    List<Reservation> getTeamReservationsByMonth(YearMonth yearMonth, UUID id);

    List<Reservation> getRoomReservationsByMonth(YearMonth yearMonth, UUID id);
}
