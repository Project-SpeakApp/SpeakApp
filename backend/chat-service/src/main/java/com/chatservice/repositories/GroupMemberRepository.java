package com.chatservice.repositories;

import com.chatservice.entities.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {

    @Query("SELECT gr FROM group_member gr WHERE gr.conversation.isGroupConversation = false AND (gr.userId = :userId1 OR gr.userId = :userId2)")
    List<GroupMember> findGroupMemberByUserIdsAndConversationIsPrivate(UUID userId1, UUID userId2);

    @Query("SELECT DISTINCT gr.conversation.conversationId FROM group_member gr WHERE gr.userId = :userId")
    List<UUID> findConversationsByGroupMember(UUID userId);

    @Query("SELECT DISTINCT gr.userId FROM group_member gr WHERE gr.conversation.conversationId = :conversationId")
    List<UUID> findUserIdsByConversation(UUID conversationId);

}
