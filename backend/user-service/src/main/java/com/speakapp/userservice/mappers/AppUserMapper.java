package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.AppUserDTO;
import com.speakapp.userservice.dtos.AppUserPreviewDTO;
import com.speakapp.userservice.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppUserMapper {
    AppUserDTO toAppUserDto(AppUser appUser);

    @Mapping(target = "fullName",
            expression = "java(concatenateNames(appUser.getFirstName(), appUser.getLastName()))")
    AppUserPreviewDTO appUserPreviewDtoFromAppUser(AppUser appUser);

    default String concatenateNames(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}