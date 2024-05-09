package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserCreateDTO;
import com.speakapp.userservice.dtos.AppUserGetDTO;
import com.speakapp.userservice.dtos.AppUserUpdateDTO;
import com.speakapp.userservice.dtos.PhotoUpdateDTO;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.services.UserService;
import com.speakapp.userservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserGetDTO getUserById(@PathVariable(name = "userId") UUID userId)
            throws UserNotFoundException {
        return userService.getUser(userId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody AppUserCreateDTO userCreateDTO) {
        userService.createUser(userCreateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AppUserGetDTO updateUserInfo(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody AppUserUpdateDTO userUpdateDTO) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return userService.updateUserInfo(userId, userUpdateDTO);
    }

    @PutMapping("/profile-photo")
    @ResponseStatus(HttpStatus.OK)
    public AppUserGetDTO updateUserProfilePhoto(@RequestHeader(name = "Authorization") String authHeader,
                                                @RequestBody PhotoUpdateDTO photoUpdateDTO) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return userService.updateUserProfilePhoto(userId, photoUpdateDTO);
    }

    @PutMapping("/background-photo")
    @ResponseStatus(HttpStatus.OK)
    public AppUserGetDTO updateUserBackgroundPhoto(@RequestHeader(name = "Authorization") String authHeader,
                                                   @RequestBody PhotoUpdateDTO photoUpdateDTO) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return userService.updateUserBackgroundPhoto(userId, photoUpdateDTO);
    }
}
