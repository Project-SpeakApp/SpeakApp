package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.AppUserPreviewDTO;
import com.speakapp.userservice.dtos.AppUserPreviewInternalDTO;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.AppUserMapper;
import com.speakapp.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;

    public Map<UUID, AppUserPreviewInternalDTO> getUsersPreviews(Set<UUID> userIds) {
        return userIds.stream()
                .collect(Collectors.toMap(
                        userId -> userId,
                        userId -> userRepository.findById(userId)
                                .map(appUserMapper::toAppUserPreviewInternalDto)
                                .orElse(AppUserPreviewInternalDTO.empty())
                ));
    }

    public AppUserPreviewDTO getUserPreview(UUID userId) {
        return userRepository.findById(userId)
                .map(appUserMapper::toAppUserPreviewDto)
                .orElseThrow(UserNotFoundException::new);
    }
}
