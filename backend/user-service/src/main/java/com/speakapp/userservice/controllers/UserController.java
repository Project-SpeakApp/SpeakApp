package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserDTO;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserDTO getUserById(@PathVariable(name = "userId") UUID userId)
            throws UserNotFoundException {
        log.info("Getting user info of user with id = {}", userId);
        return userService.getUser(userId);
    }
}
