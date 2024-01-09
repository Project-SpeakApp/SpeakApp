package com.speakapp.authservice.controllers;

import com.speakapp.authservice.dtos.LoginResponseDTO;
import com.speakapp.authservice.dtos.UserLoginDTO;
import com.speakapp.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return authService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
    }
}
