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
//  allow adding reservations only when the given room is available at a given time

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
            throw new IllegalStateException("Reservation cannot end before it starts!");

        Room chosenRoom = roomRepository.findById(reservation.getRoomId()).orElse(null);
        Team userTeam = teamRepository.findById(currentUser.teamId()).orElse(null);
        assert chosenRoom != null;
        assert userTeam != null;
        if (userTeam.getSize() > chosenRoom.getSeats())
            System.err.println("Room may not accommodate whole team!");

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

        Reservation existingReservation = reservationRepository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Reservation not found!")
                );

        if (!Objects.equals(existingReservation.getOwnerId(), currentUser.id()))
            throw new IllegalArgumentException("Cannot modify other users' reservations!");

        if ((reservation.getStart() != null && reservation.getEnd() != null
                && reservation.getEnd().isBefore(reservation.getStart())
        ) || (reservation.getStart() != null && reservation.getEnd() == null
                    && existingReservation.getEnd().isBefore(reservation.getStart())
        ) || (reservation.getStart() == null && reservation.getEnd() != null
                    && reservation.getEnd().isBefore(existingReservation.getStart())
        )) {
            throw new IllegalArgumentException("Reservation cannot end before it starts!");
        }

        if (reservation.getTitle() != null)
            existingReservation.setTitle(reservation.getTitle());

        if (reservation.getDescription() != null)
            existingReservation.setDescription(reservation.getDescription());

        if (reservation.getStart() != null)
            existingReservation.setStart(reservation.getStart());

        if (reservation.getEnd() != null)
            existingReservation.setEnd(reservation.getEnd());

        if (reservation.getRoomId() != null)
            existingReservation.setRoomId(reservation.getRoomId());

        if (reservation.getTeamId() != null)
            existingReservation.setTeamId(reservation.getTeamId());

        if (Objects.equals(reservation.getOwnerId(), currentUser.id()))
            existingReservation.setOwnerId(reservation.getOwnerId());

        existingReservation.setUpdated(LocalDateTime.now());

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

        if (!Objects.equals(existingReservation.getOwnerId(), currentUser.id()))
            throw new IllegalArgumentException("Cannot modify other users' reservations!");

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
}
