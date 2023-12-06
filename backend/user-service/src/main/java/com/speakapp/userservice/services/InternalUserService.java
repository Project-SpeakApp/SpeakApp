package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.AppUserPreviewDTO;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.mappers.AppUserMapper;
import com.speakapp.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;
    public List<AppUserPreviewDTO> getUsersPreviews(List<UUID> userIds) {

        List<AppUserPreviewDTO> userPreviewDTOS = new ArrayList<>(userIds.size());
        for(UUID userId : userIds) {
            Optional<AppUser> appUser = userRepository.findById(userId);

            AppUserPreviewDTO userPreviewDTO = appUser
                    .map(appUserMapper::appUserPreviewDtoFromAppUser)
                    .orElse(AppUserPreviewDTO.empty(userId));

            userPreviewDTOS.add(userPreviewDTO);
        }
        return userPreviewDTOS;
    }
}
