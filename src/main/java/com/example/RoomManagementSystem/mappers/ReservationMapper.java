package com.example.RoomManagementSystem.mappers;

import com.example.RoomManagementSystem.domain.dto.ReservationDto;
import com.example.RoomManagementSystem.domain.entities.Reservation;

public interface ReservationMapper {

    Reservation fromDto(ReservationDto reservationDto);

    ReservationDto toDto(Reservation reservation);
}
