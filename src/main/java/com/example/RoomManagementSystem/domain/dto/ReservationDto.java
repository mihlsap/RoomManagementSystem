package com.example.RoomManagementSystem.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDto(
        UUID id,
        String title,
        String description,
        LocalDateTime date
) {
}
