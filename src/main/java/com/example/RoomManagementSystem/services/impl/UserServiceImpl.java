package com.example.RoomManagementSystem.services.impl;

import com.example.RoomManagementSystem.domain.dto.UserDto;
import com.example.RoomManagementSystem.repositories.TeamRepository;
import com.example.RoomManagementSystem.services.UserService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final TeamRepository teamRepository;

    public UserServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public UserDto getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication) || ! (authentication instanceof KeycloakAuthenticationToken keycloakAuthenticationToken))
            throw new IllegalStateException("No authenticated user found or invalid authentication type!");

        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) keycloakAuthenticationToken.getPrincipal();
        AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();

        return new UserDto(
                UUID.fromString(accessToken.getId()),
                accessToken.getPreferredUsername(),
                accessToken.getGivenName(),
                accessToken.getFamilyName(),
                accessToken.getEmail(),
                Objects.requireNonNull(
                        teamRepository.findById(UUID.fromString(accessToken.getId()))
                                .orElse(null))
                        .getId()
        );
    }
}
