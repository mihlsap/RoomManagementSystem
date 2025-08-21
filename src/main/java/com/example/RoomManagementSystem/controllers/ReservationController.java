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

    @PostMapping
    public ReservationDto addReservation(@RequestBody ReservationDto reservationDto) {
        return reservationMapper.toDto(reservationService.createReservation(reservationMapper.fromDto(reservationDto)));
    }

    @PutMapping("{reservation_id}")
    public ReservationDto updateReservation(@PathVariable("reservation_id") UUID reservationId, @RequestBody ReservationDto reservationDto) {
        return reservationMapper.toDto(reservationService.updateReservation(reservationId, reservationMapper.fromDto(reservationDto)));
    }

    @DeleteMapping("{reservation_id}")
    public void deleteReservation(@PathVariable("reservation_id") UUID id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("{reservation_id}")
    public ReservationDto getReservation(@PathVariable("reservation_id") UUID id) {
        return reservationMapper.toDto(reservationService.getReservation(id).orElse(null));
    }

    @GetMapping("/my-reservations")
    public List<ReservationDto> getCurrentUserReservations() {
        return reservationService
                .getCurrentUserReservations()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }
}
