package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.*;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.entities.FriendStatus;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.AppUserMapper;
import com.speakapp.userservice.repositories.UserFriendRepository;
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
    private final UserFriendRepository userFriendRepository;

    public AppUserWithFriendStatusGetDTO getUser(UUID requesterId, UUID userIdToFetch) throws UserNotFoundException {
        AppUser requester = userRepository.findById(requesterId)
                .orElseThrow(UserNotFoundException::new);
        AppUser userToFetch = userRepository.findById(userIdToFetch)
                .orElseThrow(UserNotFoundException::new);

        String friendStatus = userFriendRepository.findFriendStatusByAddresseeAndRequester(
                        requester,
                        userToFetch
                )
                .map(fs -> {
                    if (fs == FriendStatus.REQUEST) {
                        return fs.name() + " TO ACCEPT";
                    }
                    return fs.name();
                })
                .orElse(null);
        if (friendStatus == null) {
            friendStatus = userFriendRepository.findFriendStatusByAddresseeAndRequester(
                            userToFetch,
                            requester
                    ).map(fs -> {
                        if (fs == FriendStatus.REQUEST) {
                            return fs.name() + " SENT";
                        }
                        return fs.name();
                    })
                    .orElse(null);
        }

        return appUserMapper.toGetDTO(userToFetch, friendStatus);
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
}
