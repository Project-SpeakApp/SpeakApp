package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import org.mapstruct.*;

@Mapper
public interface AppUserMapper {

    @Mapping(target = "profilePhotoUrl", constant = "")
    @Mapping(target = "lastOnline", expression = "java(java.time.Instant.now())")
    @Mapping(target = "bgPhotoUrl", constant = "")
    @Mapping(target = "about", constant = "")
    AppUser toEntity(AppUserCreateDTO appUserCreateDTO);

    AppUserGetDTO toGetDTO(AppUser appUser);

    AppUserWithFriendStatusGetDTO toGetDTO(AppUser appUser,
                                           String friendStatus);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateAppUserFromAppUserUpdateDTO(AppUserUpdateDTO appUserUpdateDTO,
                                           @MappingTarget AppUser appUser);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "profilePhotoUrl", source = "photoUpdateDTO.photoUrl")
    void updateAppUserProfilePhotoFromPhotoUpdateDTO(PhotoUpdateDTO photoUpdateDTO,
                                                     @MappingTarget AppUser appUser);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "bgPhotoUrl", source = "photoUpdateDTO.photoUrl")
    void updateAppUserBackgroundPhotoFromPhotoUpdateDTO(PhotoUpdateDTO photoUpdateDTO,
                                                        @MappingTarget AppUser appUser);

    @Mapping(target = "fullName",
            expression = "java(concatenateNames(appUser.getFirstName(), appUser.getLastName()))")
    AppUserPreviewDTO toAppUserPreviewDto(AppUser appUser);

    default String concatenateNames(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}