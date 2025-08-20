package com.example.RoomManagementSystem.controllers;

import com.example.RoomManagementSystem.domain.dto.ReservationDto;
import com.example.RoomManagementSystem.mappers.ReservationMapper;
import com.example.RoomManagementSystem.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "reservations")
public class ReservationController {

    //TODO:
    // add check whether the reserved room has enough seats for the team, if not notify user

    private final ReservationService reservationService;

    private final ReservationMapper reservationMapper;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @GetMapping("all")
    private List<ReservationDto> getAllReservations() {
        return reservationService
                .getAllReservations()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("teams/{team_id}")
    public List<ReservationDto> getTeamReservations(@PathVariable("team_id") UUID id) {
        return reservationService
                .getTeamReservations(id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("rooms/{room_id}")
    public List<ReservationDto> getRoomReservations(@PathVariable("room_id") UUID roomId) {
        return reservationService
                .getRoomReservations(roomId)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("users/{user_id}")
    public List<ReservationDto> getUserReservations(@PathVariable("user_id") UUID userId) {
        return reservationService
                .getUserReservations(userId)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @PostMapping("users/{user_id}")
    public ReservationDto addReservation(@PathVariable("user_id") UUID userId, @RequestBody ReservationDto reservationDto) {
        return reservationMapper.toDto(reservationService.createReservation(reservationMapper.fromDto(reservationDto)));
    }

    @PutMapping(path = "users/{user_id}/{reservation_id}")
    public ReservationDto updateReservation(@PathVariable("user_id") UUID userId, @PathVariable("reservation_id") UUID reservationId, @RequestBody ReservationDto reservationDto) {
        return reservationMapper.toDto(reservationService.updateReservation(userId, reservationId, reservationMapper.fromDto(reservationDto)));
    }

    @DeleteMapping(path = "users/{user_id}/{reservation_id}")
    public void deleteReservation(@PathVariable("user_id") UUID userId, @PathVariable("reservation_id") UUID id) {
        reservationService.deleteReservation(userId, id);
    }

    @GetMapping(path = "users/{user_id}/{reservation_id}")
    public ReservationDto getReservation(@PathVariable("user_id") UUID userId, @PathVariable("reservation_id") UUID id) {
        return reservationMapper.toDto(reservationService.getReservation(id).orElse(null));
    }
}
