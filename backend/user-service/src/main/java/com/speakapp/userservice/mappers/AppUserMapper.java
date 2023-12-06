package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AppUserMapper {
    AppUser appUserFromAppUserCreateDTO(AppUserCreateDTO appUserCreateDTO);
    AppUserDTO appUserDtoFomAppUser(AppUser appUser);
    void updateAppUserFromAppUserUpdateDTO(AppUserUpdateDTO appUserUpdateDTO,
                                           @MappingTarget AppUser appUser);
    @Mapping(target = "profilePhotoUrl", source = "photoUpdateDTO.photoUrl")
    void updateAppUserProfilePhotoFromPhotoUpdateDTO(PhotoUpdateDTO photoUpdateDTO,
                                                            @MappingTarget AppUser appUser);
    @Mapping(target = "bgPhotoUrl", source = "photoUpdateDTO.photoUrl")
    void updateAppUserBackgroundPhotoFromPhotoUpdateDTO(PhotoUpdateDTO photoUpdateDTO,
                                                        @MappingTarget AppUser appUser);
}