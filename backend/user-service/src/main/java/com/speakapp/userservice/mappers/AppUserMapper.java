package com.speakapp.userservice.mappers;

import com.speakapp.userservice.dtos.AppUserCreateDTO;
import com.speakapp.userservice.dtos.AppUserGetDTO;
import com.speakapp.userservice.dtos.AppUserUpdateDTO;
import com.speakapp.userservice.dtos.PhotoUpdateDTO;
import com.speakapp.userservice.entities.AppUser;
import org.mapstruct.*;

@Mapper
public interface AppUserMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "profilePhotoUrl", constant = "")
    @Mapping(target = "lastOnline", constant = "")
    @Mapping(target = "bgPhotoUrl", constant = "")
    @Mapping(target = "about", constant = "")
    AppUser toEntity(AppUserCreateDTO appUserCreateDTO);

    AppUserGetDTO toGetDTO(AppUser appUser);

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
}