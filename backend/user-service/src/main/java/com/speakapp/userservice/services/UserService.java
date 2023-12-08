package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.AppUserMapper;
import com.speakapp.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void createUser(AppUserCreateDTO userDTO) {
        AppUser appUser = appUserMapper.toEntity(userDTO);
        appUser.updateLastOnline();
        userRepository.save(appUser);
    }

    public AppUserGetDTO updateUserInfo(UUID userId, AppUserUpdateDTO appUserUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(() -> new UserNotFoundException(userId));

        appUserMapper.updateAppUserFromAppUserUpdateDTO(appUserUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return appUserMapper.toGetDTO(updatedUser);
    }

    public AppUserGetDTO updateUserProfilePhoto(UUID userId, PhotoUpdateDTO photoUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(() -> new UserNotFoundException(userId));

        appUserMapper.updateAppUserProfilePhotoFromPhotoUpdateDTO(photoUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return appUserMapper.toGetDTO(updatedUser);
    }

    public AppUserGetDTO updateUserBackgroundPhoto(UUID userId, PhotoUpdateDTO photoUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(() -> new UserNotFoundException(userId));

        appUserMapper.updateAppUserBackgroundPhotoFromPhotoUpdateDTO(photoUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return appUserMapper.toGetDTO(updatedUser);
    }

    public void deleteUser(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}
