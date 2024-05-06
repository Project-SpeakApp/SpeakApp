package com.speakapp.userservice.repositories;

import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.entities.FriendStatus;
import com.speakapp.userservice.entities.UserFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, UUID> {

    boolean existsByRequesterAndAddressee(AppUser requester,
                                          AppUser addressee);

    Optional<UserFriend> findByIdAndAddresseeAndStatus(UUID id,
                                                       AppUser addressee,
                                                       FriendStatus status);

    void deleteByIdAndAddresseeAndStatus(UUID id,
                                         AppUser addressee,
                                         FriendStatus status);

    Page<UserFriend> findAllByAddresseeAndStatusOrderByCreatedAtDesc(AppUser addressee,
                                                                     FriendStatus status,
                                                                     Pageable pageable);

    Page<UserFriend> findAllByAddresseeOrRequesterAndStatusOrderByCreatedAt(AppUser addressee,
                                                                            AppUser requester,
                                                                            FriendStatus status,
                                                                            Pageable pageable);

    void deleteByAddresseeAndRequester(AppUser addressee, AppUser requester);
}
