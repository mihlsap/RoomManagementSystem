package com.example.RoomManagementSystem.services.impl;

import com.example.RoomManagementSystem.domain.dto.UserDto;
import com.example.RoomManagementSystem.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    public UserDto getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication: " + authentication);
        System.out.println("Class: " + (authentication != null ? authentication.getClass() : "null"));

        if (Objects.isNull(authentication) || ! (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken))
            throw new IllegalStateException("No authenticated user found or invalid authentication type!");

        Jwt accessToken = jwtAuthenticationToken.getToken();

        if (accessToken.getClaimAsString("preferred_username") == null || accessToken.getClaimAsString("preferred_username").isEmpty())
            throw new IllegalStateException("No username found!");
        if (accessToken.getClaimAsString("email") == null || accessToken.getClaimAsString("email").isEmpty())
            throw new IllegalStateException("No email found!");
        if (accessToken.getClaimAsString("given_name") == null || accessToken.getClaimAsString("given_name").isEmpty())
            throw new IllegalStateException("No name found!");
        if (accessToken.getClaimAsString("family_name") == null || accessToken.getClaimAsString("family_name").isEmpty())
            throw new IllegalStateException("No surname found!");
        if (accessToken.getClaimAsString("team_id") == null || accessToken.getClaimAsString("team_id").isEmpty())
            throw new IllegalStateException("No team id found!");

        return new UserDto(
                UUID.fromString(accessToken.getSubject()),
                accessToken.getClaimAsString("preferred_username"),
                accessToken.getClaimAsString("given_name"),
                accessToken.getClaimAsString("family_name"),
                accessToken.getClaimAsString("email"),
                UUID.fromString(accessToken.getClaimAsString("team_id"))
        );
    }
}
