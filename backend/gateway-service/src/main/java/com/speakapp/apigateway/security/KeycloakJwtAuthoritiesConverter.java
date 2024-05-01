package com.speakapp.apigateway.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KeycloakJwtAuthoritiesConverter implements
        Converter<Jwt, Collection<? extends GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
    private static final String REALM_ACCESS = "realm_access";
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String ROLES = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    @Value("${app.jwt.resource-client-id}")
    private String clientId;

    @Override
    public Collection<? extends GrantedAuthority> convert(@NonNull Jwt source) {
        Stream<SimpleGrantedAuthority> roles = getRoles(source);
        Stream<GrantedAuthority> scopeAccess = getScopeAccess(source);

        return Stream.concat(roles, scopeAccess)
                .collect(Collectors.toSet());
    }

    private Stream<SimpleGrantedAuthority> getRoles(Jwt jwt) {
        Stream<SimpleGrantedAuthority> realmAccess = getRealmAccess(jwt);
        Stream<SimpleGrantedAuthority> resourceAccess = getResourceAccess(jwt);
        return Stream.concat(realmAccess, resourceAccess);
    }

    @SuppressWarnings("unchecked")
    private Stream<SimpleGrantedAuthority> getResourceAccess(Jwt jwt) {
        return Optional.of(jwt)
                .map(token -> token.getClaimAsMap(RESOURCE_ACCESS))
                .map(claimMap -> (Map<String, Object>) claimMap.get(clientId))
                .map(resourceData -> (Collection<String>) resourceData.get(ROLES))
                .stream()
                .flatMap(Collection::stream)
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .distinct();
    }

    @SuppressWarnings("unchecked")
    private Stream<SimpleGrantedAuthority> getRealmAccess(Jwt jwt) {
        return Optional.of(jwt)
                .map(token -> token.getClaimAsMap(REALM_ACCESS))
                .map(resourceData -> (Collection<String>) resourceData.get(ROLES))
                .stream()
                .flatMap(Collection::stream)
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .distinct();
    }

    private Stream<GrantedAuthority> getScopeAccess(Jwt jwt) {
        return Optional.of(jwtGrantedAuthoritiesConverter.convert(jwt))
                .stream()
                .flatMap(Collection::stream)
                .distinct();
    }

}