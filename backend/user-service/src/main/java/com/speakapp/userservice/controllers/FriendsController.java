package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.FriendRequestsPage;
import com.speakapp.userservice.services.FriendsService;
import com.speakapp.userservice.utils.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;

    @PostMapping("/send-request/{addresseeId}")
    @ResponseStatus(HttpStatus.OK)
    public void sendFriendRequest(@RequestHeader("Authorization") String authHeader,
                                  @PathVariable UUID addresseeId) {
        UUID requesterId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        friendsService.sendFriendRequest(requesterId, addresseeId);
    }

    @PostMapping("/accept-request/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptFriendRequest(@RequestHeader(name = "Authorization") String authHeader,
                                    @PathVariable UUID requestId) {
        UUID accountOwnerId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        friendsService.acceptFriendRequest(accountOwnerId, requestId);
    }

    @PostMapping("/reject-request/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public void rejectFriendRequest(@RequestHeader(name = "Authorization") String authHeader,
                                    @PathVariable UUID requestId) {
        UUID accountOwnerId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        friendsService.rejectFriendRequest(accountOwnerId, requestId);
    }

    @GetMapping("/requests")
    @ResponseStatus(HttpStatus.OK)
    public FriendRequestsPage findRequestsByAddressee(@RequestHeader(name = "Authorization") String authHeader,
                                                      @RequestParam(defaultValue = "0") int pageNumber,
                                                      @RequestParam(defaultValue = "8") int pageSize) {
        UUID accountOwnerId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        Pageable requestedPageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        return friendsService.findRequestsByAddressee(accountOwnerId, requestedPageable);
    }

    @DeleteMapping("/{userToRemoveId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserFromFriendsList(@RequestHeader(name = "Authorization") String authHeader,
                                          @PathVariable UUID userToRemoveId) {
        UUID accountOwnerId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        friendsService.removeUserFromFriendsList(accountOwnerId, userToRemoveId);
    }

}
