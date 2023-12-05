package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserAppMapper {
    AppUser appUserFromAppUserCreateDTO(AppUserCreateDTO appUserCreateDTO);
    AppUserDTO appUserDtoFomAppUser(AppUser appUser);
    void updateAppUserFromAppUserUpdateDTO(AppUserUpdateDTO appUserUpdateDTO,
                                           @MappingTarget AppUser appUser);
    @Mapping(target = "profilePhoto", source = "photoUpdateDTO.photoUrl")
    void updateAppUserProfilePhotoFromPhotoUpdateDTO(PhotoUpdateDTO photoUpdateDTO,
                                                            @MappingTarget AppUser appUser);
    @Mapping(target = "bgPhoto", source = "photoUpdateDTO.photoUrl")
    void updateAppUserBackgroundPhotoFromPhotoUpdateDTO(PhotoUpdateDTO photoUpdateDTO,
                                                        @MappingTarget AppUser appUser);
}