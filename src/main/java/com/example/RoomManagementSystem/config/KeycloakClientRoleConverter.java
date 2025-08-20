package com.example.RoomManagementSystem.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakClientRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String clientId;

    public KeycloakClientRoleConverter(String userId) {
        this.clientId = userId;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Object resourceAccessObject = jwt.getClaims().get("resource_access");

        if (! (resourceAccessObject instanceof Map<?, ?> resourceAccessMap))
            return Collections.emptyList();

        Object clientObject = resourceAccessMap.get(clientId);

        if (! (clientObject instanceof Map<?, ?> clientAccessMap))
            return Collections.emptyList();

        Object rolesObject = clientAccessMap.get("roles");

        if (! (rolesObject instanceof Collection<?> roles))
            return Collections.emptyList();

        return roles
                .stream()
                .filter(role -> role instanceof String)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
