package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.AppUserPreviewDTO;
import com.speakapp.userservice.mappers.AppUserMapper;
import com.speakapp.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;
    public List<AppUserPreviewDTO> getUsersPreviews(List<UUID> userIds) {
        return userIds.stream()
                .map(userId -> userRepository.findById(userId)
                        .map(appUserMapper::toAppUserPreviewDto)
                        .orElse(AppUserPreviewDTO.empty(userId)))
                .toList();
    }
}
