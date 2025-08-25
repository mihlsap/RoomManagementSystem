package com.example.RoomManagementSystem.controllers;

import com.example.RoomManagementSystem.domain.dto.ReservationDto;
import com.example.RoomManagementSystem.mappers.ReservationMapper;
import com.example.RoomManagementSystem.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
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

    @GetMapping("teams/{team_id}/all")
    public List<ReservationDto> getTeamReservations(@PathVariable("team_id") UUID id) {
        return reservationService
                .getTeamReservations(id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("rooms/{room_id}/all")
    public List<ReservationDto> getRoomReservations(@PathVariable("room_id") UUID roomId) {
        return reservationService
                .getRoomReservations(roomId)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("users/{user_id}/all")
    public List<ReservationDto> getUserReservations(@PathVariable("user_id") UUID userId) {
        return reservationService
                .getUserReservations(userId)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @PostMapping
    public ReservationDto addReservation(@RequestBody ReservationDto reservationDto) {
        return reservationMapper
                .toDto(reservationService
                        .createReservation(reservationMapper.fromDto(reservationDto)));
    }

    @PutMapping("{reservation_id}")
    public ReservationDto updateReservation(@PathVariable("reservation_id") UUID reservationId,
                                            @RequestBody ReservationDto reservationDto) {
        return reservationMapper
                .toDto(reservationService
                        .updateReservation(reservationId, reservationMapper.fromDto(reservationDto)));
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

    @GetMapping("/all")
    public List<ReservationDto> getReservationsByDate(@RequestParam(name = "date") LocalDate date) {
        return reservationService
                .getReservationsByDate(date)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/my-reservations")
    public List<ReservationDto> getCurrentUserReservationsByDate(@RequestParam(name = "date") LocalDate date) {
        return reservationService
                .getCurrentUserReservationsByDate(date)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/users/{user_id}")
    public List<ReservationDto> getUserReservationsByDate(@RequestParam(name = "date") LocalDate date,
                                                          @PathVariable("user_id") UUID id) {
        return reservationService
                .getUserReservationsByDate(date, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/teams/{team_id}")
    public List<ReservationDto> getTeamReservationsByDate(@RequestParam(name = "date") LocalDate date,
                                                          @PathVariable("team_id") UUID id) {
        return reservationService
                .getTeamReservationsByDate(date, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/rooms/{room_id}")
    public List<ReservationDto> getRoomReservationsByDate(@RequestParam(name = "date") LocalDate date,
                                                          @PathVariable("room_id") UUID id) {
        return reservationService
                .getRoomReservationsByDate(date, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/all/today")
    public List<ReservationDto> getTodayReservations() {
        return reservationService
                .getTodayReservations()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("my-reservations/today")
    public List<ReservationDto> getTodayCurrentUserReservations() {
        return reservationService
                .getTodayCurrentUserReservations()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/users/{user_id}/today")
    public List<ReservationDto> getTodayUserReservations(@PathVariable("user_id") UUID id) {
        return reservationService
                .getTodayUserReservations(id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/teams/{team_id}/today")
    public List<ReservationDto> getTodayTeamReservations(@PathVariable("team_id") UUID id) {
        return reservationService
                .getTodayTeamReservations(id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/rooms/{room_id}/today")
    public List<ReservationDto> getTodayRoomReservations(@PathVariable("room_id") UUID id) {
        return reservationService
                .getTodayRoomReservations(id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/all")
    public List<ReservationDto> getReservationsByWeek(@RequestParam("year") int year, @RequestParam("week") int week) {
        return reservationService
                .getReservationsByWeek(year, week)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/my-reservations")
    public List<ReservationDto> getCurrentUserReservationsByWeek(@RequestParam("year") int year,
                                                                 @RequestParam("week") int week) {
        return reservationService
                .getCurrentUserReservationsByWeek(year, week)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/teams/{team_id}")
    public List<ReservationDto> getTeamReservationsByWeek(@PathVariable("team_id") UUID id,
                                                          @RequestParam("year") int year,
                                                          @RequestParam("week") int week) {
        return reservationService
                .getTeamReservationsByWeek(year, week, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/rooms/{room_id}")
    public List<ReservationDto> getRoomReservationsByWeek(@PathVariable("room_id") UUID id,
                                                          @RequestParam("year") int year,
                                                          @RequestParam("week") int week) {
        return reservationService
                .getRoomReservationsByWeek(year, week, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/all")
    public List<ReservationDto> getReservationsByMonth(@RequestParam("yearMonth") YearMonth yearMonth) {
        return reservationService
                .getReservationsByMonth(yearMonth)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/my-reservations")
    public List<ReservationDto> getCurrentUserReservationsByMonth(@RequestParam("yearMonth") YearMonth yearMonth) {
        return reservationService
                .getCurrentUserReservationsByMonth(yearMonth)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/users/{user_id}")
    public List<ReservationDto> getUserReservationsByMonth(@PathVariable("user_id") UUID id,
                                                           @RequestParam("yearMonth") YearMonth yearMonth) {
        return reservationService
                .getUserReservationsByMonth(yearMonth, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/teams/{team_id}")
    public List<ReservationDto> getTeamReservationsByMonth(@PathVariable("team_id") UUID id,
                                                           @RequestParam("yearMonth") YearMonth yearMonth) {
        return reservationService
                .getTeamReservationsByMonth(yearMonth, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    @GetMapping("/rooms/{room_id}")
    public List<ReservationDto> getRoomReservationsByMonth(@PathVariable("room_id") UUID id,
                                                           @RequestParam("yearMonth") YearMonth yearMonth) {
        return reservationService
                .getRoomReservationsByMonth(yearMonth, id)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }
}
