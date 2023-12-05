package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserCreateDTO;
import com.speakapp.userservice.dtos.AppUserDTO;
import com.speakapp.userservice.dtos.AppUserUpdateDTO;
import com.speakapp.userservice.dtos.PhotoUpdateDTO;
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
    public AppUserDTO getUserById(@RequestHeader(name = "UserId") UUID userId)
            throws UserNotFoundException {
        return userService.getUser(userId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDTO createUser(@RequestBody AppUserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDTO updateUserInfo(@RequestHeader(name = "UserId") UUID userId,
                                     @RequestBody AppUserUpdateDTO userUpdateDTO) {
        return userService.updateUserInfo(userId, userUpdateDTO);
    }

    @PutMapping("/profile-photo")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDTO updateUserProfilePhoto(@RequestHeader(name = "UserId") UUID userId,
                                     @RequestBody PhotoUpdateDTO photoUpdateDTO) {
        return userService.updateUserProfilePhoto(userId, photoUpdateDTO);
    }

    @PutMapping("/background-photo")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDTO updateUserBackgroundPhoto(@RequestHeader(name = "UserId") UUID userId,
                                             @RequestBody PhotoUpdateDTO photoUpdateDTO) {
        return userService.updateUserBackgroundPhoto(userId, photoUpdateDTO);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable(name = "userId") UUID userId)
            throws UserNotFoundException {
        userService.deleteUser(userId);
    }
}
