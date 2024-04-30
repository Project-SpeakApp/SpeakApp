package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    @Query("SELECT gm.conversation.conversationId FROM group_member gm " +
            "WHERE gm.userId = :userId1 AND gm.conversation.isGroupConversation = false " +
            "AND gm.conversation.conversationId IN " +
            "(SELECT gm2.conversation.conversationId FROM group_member gm2 WHERE gm2.userId = :userId2)")
    List<UUID> findConversationsForTwoUsers(UUID userId1, UUID userId2);

}
