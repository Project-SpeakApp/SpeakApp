package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserPreviewDTO;
import com.speakapp.userservice.services.InternalFriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/internal/friends")
@RequiredArgsConstructor
public class InternalFriendsController {

    private final InternalFriendsService internalFriendsService;

    @GetMapping("/ids/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UUID> getFriendIdsOfUser(@PathVariable UUID userId) {
        return internalFriendsService.getFriendIdsOfUser(userId);
    }
}
