package com.speakapp.userservice.repositories;

import com.speakapp.userservice.entities.AppUser;
import com.speakapp.userservice.entities.FriendStatus;
import com.speakapp.userservice.entities.UserFriend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    Page<UserFriend> findAllByAddresseeAndStatusOrRequesterAndStatus(AppUser addressee,
                                                                     FriendStatus status1,
                                                                     AppUser requester,
                                                                     FriendStatus status2,
                                                                     Pageable pageable);

    void deleteByAddresseeAndRequester(AppUser addressee, AppUser requester);


    @Query("SELECT uf.status FROM UserFriend uf WHERE uf.addressee = :addressee AND uf.requester = :requester")
    Optional<FriendStatus> findFriendStatusByAddresseeAndRequester(
            @Param("addressee") AppUser addressee,
            @Param("requester") AppUser requester
    );

    @Query("SELECT CASE " +
            "WHEN uf.requester.userId = :userId THEN uf.addressee.userId " +
            "WHEN uf.addressee.userId = :userId THEN uf.requester.userId " +
            "END " +
            "FROM UserFriend uf " +
            "WHERE uf.status = 'FRIEND' " +
            "AND (uf.requester.userId = :userId OR uf.addressee.userId = :userId)")
    List<UUID> getAllFriendIdsOfUserWithId(UUID userId);
}
