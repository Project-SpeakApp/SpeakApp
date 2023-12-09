package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserPreviewDTO;
import com.speakapp.userservice.services.InternalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final InternalUserService internalUserService;

    @GetMapping("/{userIds}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppUserPreviewDTO> getUsers(@PathVariable List<UUID> userIds) {
        return internalUserService.getUsersPreviews(userIds);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserPreviewDTO getUser(@PathVariable UUID userId) {
        return internalUserService.getUserPreview(userId);
    }
}
