package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserDTO;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserDTO getUserById(@PathVariable(name = "userId") UUID userId)
            throws UserNotFoundException {
        return userService.getUser(userId);
    }
}
