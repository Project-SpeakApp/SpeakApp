package com.speakapp.authservice.mappers;

import com.speakapp.authservice.dtos.LoginResponseDTO;
import com.speakapp.authservice.dtos.TokenResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper
public interface LoginMapper {

    @Mapping(source = "tokenResponse.accessToken", target = "accessToken")
    @Mapping(source = "expiresIn", target = "expiresIn")
    @Mapping(source = "refreshExpiresIn", target = "refreshExpiresIn")
    @Mapping(source = "tokenResponse.refreshToken", target = "refreshToken")
    LoginResponseDTO toLoginResponse(UUID userId, String username, Instant expiresIn,
                                     Instant refreshExpiresIn, TokenResponseDTO tokenResponse);
}
