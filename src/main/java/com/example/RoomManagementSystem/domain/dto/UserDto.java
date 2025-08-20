package com.example.RoomManagementSystem.domain.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String firstName,
        String lastName,
        String email,
        UUID teamId
) {
}
