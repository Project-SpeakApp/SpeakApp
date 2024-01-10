package com.speakapp.authservice.mappers;

import com.speakapp.authservice.dtos.LoginResponseDTO;
import com.speakapp.authservice.dtos.TokenResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface LoginMapper {

    @Mapping(source = "tokenResponse.accessToken", target = "accessToken")
    @Mapping(source = "tokenResponse.expiresIn", target = "expiresIn")
    @Mapping(source = "tokenResponse.refreshExpiresIn", target = "refreshExpiresIn")
    @Mapping(source = "tokenResponse.refreshToken", target = "refreshToken")
    LoginResponseDTO mapToLoginResponse(UUID userId, String username, TokenResponseDTO tokenResponse);
}
