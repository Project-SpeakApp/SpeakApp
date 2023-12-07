package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.AppUserDTO;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.UserAppMapper;
import com.speakapp.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserAppMapper userAppMapper;

    public AppUserDTO getUser(UUID userId) throws UserNotFoundException {

        Optional<AppUser> user = userRepository.findById(userId);

        return user.map(userAppMapper::toAppUserDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
