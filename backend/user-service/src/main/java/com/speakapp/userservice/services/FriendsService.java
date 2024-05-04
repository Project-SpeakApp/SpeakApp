package com.speakapp.userservice.services;

import com.speakapp.userservice.dtos.FriendRequestGetDTO;
import com.speakapp.userservice.dtos.FriendRequestsPage;
import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.entities.FriendStatus;
import com.speakapp.userservice.entities.UserFriend;
import com.speakapp.userservice.exceptions.FriendRequestNotFoundException;
import com.speakapp.userservice.exceptions.InvalidFriendRequestException;
import com.speakapp.userservice.exceptions.UserNotFoundException;
import com.speakapp.userservice.mappers.FriendsMapper;
import com.speakapp.userservice.repositories.UserFriendRepository;
import com.speakapp.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FriendsService {

    private final UserFriendRepository userFriendRepository;
    private final UserRepository userRepository;
    private final FriendsMapper friendsMapper;

    public void sendFriendRequest(UUID requesterId, UUID addresseeId) {
        AppUser requester = userRepository.findById(requesterId)
                .orElseThrow(UserNotFoundException::new);
        AppUser addressee = userRepository.findById(addresseeId).
                orElseThrow(UserNotFoundException::new);

        if (userFriendRepository.existsByRequesterAndAddressee(requester, addressee)) {
            throw new InvalidFriendRequestException("Request has already been sent");
        }

        UserFriend userFriendEntity = UserFriend.builder()
                .requester(requester)
                .addressee(addressee)
                .status(FriendStatus.REQUEST)
                .build();

        userFriendRepository.save(userFriendEntity);
    }

    public void acceptFriendRequest(UUID accountOwnerId,
                                    UUID requestId) {
        AppUser accountOwner = userRepository.findById(accountOwnerId)
                .orElseThrow(UserNotFoundException::new);
        UserFriend friendRequest = userFriendRepository.findByIdAndAddresseeAndStatus(
                requestId,
                accountOwner,
                FriendStatus.REQUEST
        ).orElseThrow(FriendRequestNotFoundException::new);

        friendRequest.setStatus(FriendStatus.FRIEND);

        userFriendRepository.save(friendRequest);
    }

    @Transactional
    public void rejectFriendRequest(UUID accountOwnerId,
                                    UUID requestId) {
        AppUser accountOwner = userRepository.findById(accountOwnerId)
                .orElseThrow(UserNotFoundException::new);

        userFriendRepository.deleteByIdAndAddresseeAndStatus(
                requestId,
                accountOwner,
                FriendStatus.REQUEST
        );
    }

    public FriendRequestsPage findRequestsByAddressee(UUID addresseeId, Pageable requestedPageable) {
        AppUser addressee = userRepository.findById(addresseeId)
                .orElseThrow(UserNotFoundException::new);

        Page<UserFriend> friendsRequestsPage = userFriendRepository.findAllByAddresseeAndStatusOrderByCreatedAtDesc(
                addressee,
                FriendStatus.REQUEST,
                requestedPageable
        );

        List<FriendRequestGetDTO> friendsRequestsDTO = friendsRequestsPage
                .getContent()
                .stream()
                .map(friendsMapper::toGetDTO)
                .toList();

        return FriendRequestsPage.builder()
                .friendRequests(friendsRequestsDTO)
                .currentPage(friendsRequestsPage.getNumber())
                .totalPages(friendsRequestsPage.getTotalPages())
                .pageSize(friendsRequestsPage.getSize())
                .build();
    }

    @Transactional
    public void removeUserFromFriendsList(UUID accountOwnerId, UUID userToRemoveId) {
        AppUser accountOwner = userRepository.findById(accountOwnerId)
                .orElseThrow(UserNotFoundException::new);
        AppUser userToRemoveFromFriends = userRepository.findById(userToRemoveId)
                .orElseThrow(UserNotFoundException::new);

        userFriendRepository.deleteByAddresseeAndRequester(accountOwner, userToRemoveFromFriends);
        userFriendRepository.deleteByAddresseeAndRequester(userToRemoveFromFriends, accountOwner);
    }
}
