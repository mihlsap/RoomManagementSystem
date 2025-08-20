package com.example.RoomManagementSystem.services.impl;

import com.example.RoomManagementSystem.domain.dto.UserDto;
import com.example.RoomManagementSystem.domain.entities.Reservation;
import com.example.RoomManagementSystem.repositories.ReservationRepository;
import com.example.RoomManagementSystem.repositories.TeamRepository;
import com.example.RoomManagementSystem.services.ReservationService;
import com.example.RoomManagementSystem.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// TODO:
//  block unauthenticated users from accessing the resources,
//  block unauthenticated users from modifying and deleting reservations,
//  create Dockerfile,
//  integrate keycloak,
//  integrate google calendar api

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserService userService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation createReservation(Reservation reservation) {

        UserDto currentUser = userService.getCurrentUser();

        if (reservation.getId() != null)
            throw new IllegalStateException("Reservation already has an id!");

        if (reservation.getTitle() == null)
            throw new IllegalStateException("Reservation title is required!");

        if (reservation.getOwnerId() == null)
            throw new IllegalStateException("Reservation owner id is required!");

        if (reservation.getDate() == null)
            throw new IllegalStateException("Reservation Date is required!");

        if (reservation.getTeamId() == null)
            throw new IllegalStateException("Reservation team is required!");

        return reservationRepository.save(new Reservation(
                null,
                reservation.getTitle(),
                reservation.getDescription(),
                reservation.getDate(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                currentUser.teamId(),
                currentUser.id(),
                reservation.getRoomId()
        ));
    }

    @Override
    public Reservation updateReservation(UUID userId, UUID id, Reservation reservation) {

        UserDto currentUser = userService.getCurrentUser();

        if (!Objects.equals(userId, currentUser.id()))
            throw new IllegalStateException("Cannot modify other users' reservations!");

        if (reservation.getId() == null)
            throw new IllegalStateException("Reservation id is required!");

        if (!Objects.equals(id, reservation.getId()))
            throw new IllegalStateException("Reservation id does not match!");

        Reservation existingReservation = reservationRepository
                .findById(reservation
                        .getId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Reservation not found!")
                );

        if (!Objects.equals(existingReservation.getOwnerId(), currentUser.id()))
            throw new IllegalArgumentException("Cannot modify other users' reservations!");

        existingReservation.setTitle(reservation.getTitle());
        existingReservation.setDescription(reservation.getDescription());
        existingReservation.setDate(reservation.getDate());
        existingReservation.setRoomId(reservation.getRoomId());
        existingReservation.setTeamId(reservation.getTeamId());
        existingReservation.setOwnerId(reservation.getOwnerId());
        existingReservation.setUpdated(LocalDateTime.now());

        return reservationRepository.save(existingReservation);
    }

    @Override
    public Optional<Reservation> getReservation(UUID id) {
        return reservationRepository.findById(id);
    }

    @Override
    public void deleteReservation(UUID userId, UUID id) {

        UserDto currentUser = userService.getCurrentUser();

        if (!Objects.equals(userId, currentUser.id()))
            throw new IllegalArgumentException("Cannot modify other users' reservations!");

        Reservation existingReservation = reservationRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Reservation not found!")
                );

        if (!Objects.equals(existingReservation.getOwnerId(), currentUser.id()))
            throw new IllegalArgumentException("Cannot modify other users' reservations!");

        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getUserReservations(UUID id) {
        UserDto currentUser = userService.getCurrentUser();
        return reservationRepository.findByOwnerId(currentUser.id());
    }

    @Override
    public List<Reservation> getTeamReservations(UUID id) {
        return reservationRepository.findByTeamId(id);
    }

    @Override
    public List<Reservation> getRoomReservations(UUID id) {
        return reservationRepository.findByRoomId(id);
    }
}
