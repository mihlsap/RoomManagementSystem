package com.example.RoomManagementSystem.mappers.impl;

import com.example.RoomManagementSystem.domain.dto.ReservationDto;
import com.example.RoomManagementSystem.domain.entities.Reservation;
import com.example.RoomManagementSystem.mappers.ReservationMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation fromDto(ReservationDto reservationDto) {
        return new Reservation(
                reservationDto.id(),
                reservationDto.title(),
                reservationDto.description(),
                reservationDto.start(),
                reservationDto.end(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                reservationDto.teamId(),
                reservationDto.ownerId(),
                reservationDto.roomId()
        );
    }

    @Override
    public ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getTitle(),
                reservation.getDescription(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getOwnerId(),
                reservation.getTeamId(),
                reservation.getRoomId()
        );
    }
}
