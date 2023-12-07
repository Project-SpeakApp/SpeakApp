package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.AppUserDTO;
import com.speakapp.userservice.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper
public interface UserAppMapper {
    AppUserDTO toAppUserDto(AppUser appUser);
}