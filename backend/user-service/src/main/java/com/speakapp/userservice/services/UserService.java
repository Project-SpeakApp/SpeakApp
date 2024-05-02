package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.AppUserMapper;
import com.speakapp.userservice.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;

    public AppUserGetDTO getUser(UUID userId) throws UserNotFoundException {
        Optional<AppUser> user = userRepository.findById(userId);

        return user.map(appUserMapper::toGetDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    public AppUserPreviewPageDTO getUsersByFullName(String appUserFullName, int pageNumber, int pageSize){
        String appUserFirstName;
        String appUserLastName;

        String[] fullNameParts = appUserFullName.trim().split("\\s+", 2);

        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<AppUser> appUsersPage;

        if (fullNameParts.length == 2) {
            appUserFirstName = fullNameParts[0];
            appUserLastName = fullNameParts[1];
            appUsersPage = userRepository.findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(appUserFirstName, appUserLastName, page);
        } else if(fullNameParts.length == 1 && !appUserFullName.isEmpty()) {
            appUserFirstName = fullNameParts[0];
            appUsersPage = userRepository.findAllByFirstNameEqualsIgnoreCase(appUserFirstName, page);
        } else {
            appUsersPage = userRepository.findAll(page);
        }


        return createAppUserPreviewPageDTOFromAppUserPage(appUsersPage);
    }

    public AppUserPreviewPageDTO createAppUserPreviewPageDTOFromAppUserPage(Page<AppUser> appUsersPage){
        List<AppUserPreviewDTO> appUserPreviewDTOS = appUsersPage.getContent().stream().map( appUser ->
                AppUserPreviewDTO.builder()
                .userId(appUser.getUserId())
                .fullName(appUser.getFirstName() + " " + appUser.getLastName())
                .profilePhotoUrl(appUser.getProfilePhotoUrl())
                .build()
        ).toList();

        return AppUserPreviewPageDTO.builder()
            .appUserPreviewDTOS(appUserPreviewDTOS)
            .pageNumber(appUsersPage.getNumber())
            .pageSize(appUsersPage.getSize())
            .totalPages((long) appUsersPage.getTotalPages())
            .build();
    }


    public void createUser(AppUserCreateDTO userDTO) {
        AppUser appUser = appUserMapper.toEntity(userDTO);
        appUser.updateLastOnline();
        userRepository.save(appUser);
    }

    public AppUserGetDTO updateUserInfo(UUID userId, AppUserUpdateDTO appUserUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(UserNotFoundException::new);

        appUserMapper.updateAppUserFromAppUserUpdateDTO(appUserUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return appUserMapper.toGetDTO(updatedUser);
    }

    public AppUserGetDTO updateUserProfilePhoto(UUID userId, PhotoUpdateDTO photoUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(UserNotFoundException::new);

        appUserMapper.updateAppUserProfilePhotoFromPhotoUpdateDTO(photoUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return appUserMapper.toGetDTO(updatedUser);
    }

    public AppUserGetDTO updateUserBackgroundPhoto(UUID userId, PhotoUpdateDTO photoUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(UserNotFoundException::new);

        appUserMapper.updateAppUserBackgroundPhotoFromPhotoUpdateDTO(photoUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return appUserMapper.toGetDTO(updatedUser);
    }

    public void deleteUser(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}
