package com.speakapp.userservice.services;

import com.speakapp.userservice.repositories.UserFriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalFriendsService {

    private final UserFriendRepository userFriendRepository;

    public List<UUID> getFriendIdsOfUser(UUID userId) {
        return userFriendRepository.getAllFriendIdsOfUserWithId(userId);
    }
}
