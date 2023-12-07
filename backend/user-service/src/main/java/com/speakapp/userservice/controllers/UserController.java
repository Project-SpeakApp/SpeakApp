package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserCreateDTO;
import com.speakapp.userservice.dtos.AppUserGetDTO;
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
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserGetDTO updateUserInfo(@RequestHeader(name = "UserId") UUID userId,
                                        @RequestBody AppUserUpdateDTO userUpdateDTO) {
        return userService.updateUserInfo(userId, userUpdateDTO);
    }

    @PutMapping("/profile-photo")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserGetDTO updateUserProfilePhoto(@RequestHeader(name = "UserId") UUID userId,
                                                @RequestBody PhotoUpdateDTO photoUpdateDTO) {
        return userService.updateUserProfilePhoto(userId, photoUpdateDTO);
    }

    @PutMapping("/background-photo")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserGetDTO updateUserBackgroundPhoto(@RequestHeader(name = "UserId") UUID userId,
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
