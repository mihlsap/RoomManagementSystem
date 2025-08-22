package com.example.RoomManagementSystem.services.impl;

import com.example.RoomManagementSystem.domain.dto.UserDto;
import com.example.RoomManagementSystem.domain.entities.Reservation;
import com.example.RoomManagementSystem.domain.entities.Room;
import com.example.RoomManagementSystem.domain.entities.Team;
import com.example.RoomManagementSystem.repositories.ReservationRepository;
import com.example.RoomManagementSystem.repositories.RoomRepository;
import com.example.RoomManagementSystem.repositories.TeamRepository;
import com.example.RoomManagementSystem.services.ReservationService;
import com.example.RoomManagementSystem.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// TODO:
//  integrate google calendar api,
//  use lombok,
//  change controllers to return page, not list

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    private final TeamRepository teamRepository;

    private final UserService userService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, TeamRepository teamRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    @Override
    public Reservation createReservation(Reservation reservation) {

        UserDto currentUser = userService.getCurrentUser();

        validateInput(reservation);

        if (!isTimeSlotAvailable(reservation.getStart(), reservation.getEnd(), reservation.getRoomId()))
            throw new IllegalStateException("Time slot is not available for chosen room!");

        Room chosenRoom = roomRepository
                .findById(reservation.getRoomId())
                .orElseThrow(() -> new IllegalStateException("Room not found!"));

        Team userTeam = teamRepository
                .findById(currentUser.teamId())
                .orElseThrow(() -> new IllegalStateException("Team not found!"));

        if (userTeam.getSize() > chosenRoom.getSeats())
            System.out.println("Room may not accommodate whole team!");

        if (!doesTeamAlreadyHaveAMeeting(reservation.getStart(), reservation.getEnd(), userTeam.getId()))
            System.out.println("Team already has a meeting during that time!");

        return reservationRepository.save(new Reservation(
                null,
                reservation.getTitle(),
                reservation.getDescription(),
                reservation.getStart(),
                reservation.getEnd(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                currentUser.teamId(),
                currentUser.id(),
                reservation.getRoomId()
        ));
    }

    @Transactional
    @Override
    public Reservation updateReservation(UUID id, Reservation reservation) {

        UserDto currentUser = userService.getCurrentUser();

        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found!"));

        validateOwnership(existingReservation.getOwnerId(), currentUser.id());

        if (reservation.getTitle() != null) {
            existingReservation.setTitle(reservation.getTitle());
        }

        if (reservation.getDescription() != null) {
            existingReservation.setDescription(reservation.getDescription());
        }

        existingReservation.setUpdated(LocalDateTime.now());

        if (reservation.getRoomId() != null)
            existingReservation.setRoomId(reservation.getRoomId());

        LocalDateTime newStart = reservation.getStart() != null ? reservation.getStart() : existingReservation.getStart();
        LocalDateTime newEnd = reservation.getEnd() != null ? reservation.getEnd() : existingReservation.getEnd();

        if (newEnd.isBefore(newStart)) {
            throw new IllegalArgumentException("Reservation cannot end before it starts!");
        }

        if (!isTimeSlotAvailable(newStart, newEnd, existingReservation.getRoomId(), id)) {
            throw new IllegalStateException("Time slot is not available for chosen room!");
        }

        existingReservation.setStart(newStart);
        existingReservation.setEnd(newEnd);

        if (!doesTeamAlreadyHaveAMeeting(reservation.getStart(), reservation.getEnd(), currentUser.teamId()))
            System.out.println("Team already has a meeting during that time!");

        return reservationRepository.save(existingReservation);
    }

    @Override
    public Optional<Reservation> getReservation(UUID id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteReservation(UUID id) {

        UserDto currentUser = userService.getCurrentUser();

        Reservation existingReservation = reservationRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Reservation not found!")
                );

        validateOwnership(existingReservation.getOwnerId(), currentUser.id());

        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getUserReservations(UUID id) {
        return reservationRepository.findByOwnerId(id);
    }

    @Override
    public List<Reservation> getTeamReservations(UUID id) {
        return reservationRepository.findByTeamId(id);
    }

    @Override
    public List<Reservation> getRoomReservations(UUID id) {
        return reservationRepository.findByRoomId(id);
    }

    @Override
    public List<Reservation> getCurrentUserReservations() {
        UserDto currentUser = userService.getCurrentUser();
        return reservationRepository.findByOwnerId(currentUser.id());
    }

    private boolean isOverlapping(LocalDateTime start, LocalDateTime end, Reservation reservation) {
        return start.isBefore(reservation.getEnd()) && end.isAfter(reservation.getStart());
    }

    private boolean isTimeSlotAvailable(LocalDateTime start, LocalDateTime end, UUID roomId) {
        return getRoomReservations(roomId)
                .stream()
                .noneMatch(reservation ->
                        isOverlapping(start, end, reservation));
    }

    private boolean isTimeSlotAvailable(LocalDateTime start, LocalDateTime end, UUID roomId, UUID reservationId) {
        return getRoomReservations(roomId)
                .stream()
                .filter(reservation -> !reservation.getId().equals(reservationId))
                .noneMatch(reservation -> isOverlapping(start, end, reservation));
    }

    private void validateInput(Reservation reservation) {
        if (Objects.isNull(reservation))
            throw new IllegalArgumentException("Reservation cannot be null!");

        if (reservation.getTitle() == null)
            throw new IllegalStateException("Reservation title is required!");

        if (reservation.getStart() == null)
            throw new IllegalStateException("Reservation start date is required!");

        if (reservation.getEnd() == null)
            throw new IllegalStateException("Reservation end date is required!");

        if (reservation.getRoomId() == null)
            throw new IllegalStateException("Reservation room number is required!");

        if (reservation.getEnd().isBefore(reservation.getStart()))
            throw new IllegalStateException("Reservation end cannot be before its start!");
    }

    private void validateOwnership(UUID ownerId, UUID userId) {
        if (!Objects.equals(ownerId, userId))
            throw new IllegalArgumentException("Cannot modify other users' reservations!");
    }

    private boolean doesTeamAlreadyHaveAMeeting(LocalDateTime start, LocalDateTime end, UUID teamId) {
        return getTeamReservations(teamId)
                .stream()
                .noneMatch(reservation -> isOverlapping(start, end, reservation));
    }
}
