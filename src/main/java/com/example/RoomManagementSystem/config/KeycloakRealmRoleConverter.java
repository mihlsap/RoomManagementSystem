package com.example.RoomManagementSystem.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Object realmAccessObject = jwt.getClaim("realm_access");

        if (! (realmAccessObject instanceof Map<?, ?> realmAccessMap))
            return Collections.emptyList();

        Object rolesObject = realmAccessMap.get("roles");

        if (! (rolesObject instanceof Collection<?> roles))
            return Collections.emptyList();

        return roles
                .stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
