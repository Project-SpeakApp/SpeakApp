package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.UserAppMapper;
import com.speakapp.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAppMapper userAppMapper;

    public AppUserDTO getUser(UUID userId) throws UserNotFoundException {

        Optional<AppUser> user = userRepository.findById(userId);

        return user.map(userAppMapper::appUserDtoFomAppUser)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public AppUserDTO createUser(AppUserCreateDTO userDTO) {
        AppUser appUser = userAppMapper.appUserFromAppUserCreateDTO(userDTO);
        appUser.initializeLastOnline();
        AppUser createdUser = userRepository.save(appUser);
        return userAppMapper.appUserDtoFomAppUser(createdUser);
    }

    public AppUserDTO updateUserInfo(UUID userId, AppUserUpdateDTO appUserUpdateDTO) {

        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(() -> new UserNotFoundException(userId));

        userAppMapper.updateAppUserFromAppUserUpdateDTO(appUserUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return userAppMapper.appUserDtoFomAppUser(updatedUser);
    }

    public AppUserDTO updateUserProfilePhoto(UUID userId, PhotoUpdateDTO photoUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(() -> new UserNotFoundException(userId));

        userAppMapper.updateAppUserProfilePhotoFromPhotoUpdateDTO(photoUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return userAppMapper.appUserDtoFomAppUser(updatedUser);
    }

    public AppUserDTO updateUserBackgroundPhoto(UUID userId, PhotoUpdateDTO photoUpdateDTO) {
        Optional<AppUser> user = userRepository.findById(userId);
        AppUser appUser = user.orElseThrow(() -> new UserNotFoundException(userId));

        userAppMapper.updateAppUserBackgroundPhotoFromPhotoUpdateDTO(photoUpdateDTO, appUser);

        AppUser updatedUser = userRepository.save(appUser);
        return userAppMapper.appUserDtoFomAppUser(updatedUser);
    }

    public void deleteUser(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}
