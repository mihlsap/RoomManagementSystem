package com.example.RoomManagementSystem.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

public class DynamicClientRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess == null || resourceAccess.isEmpty()) {
            return Collections.emptyList();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Object clientAccessObj : resourceAccess.values()) {
            if (clientAccessObj instanceof Map<?, ?> clientAccessMap) {
                Object rolesObj = clientAccessMap.get("roles");
                if (rolesObj instanceof Collection<?> roles) {
                    authorities.addAll(
                            roles.stream()
                                    .filter(role -> role instanceof String)
                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                    .toList()
                    );
                }
            }
        }
        return authorities;
    }
}
