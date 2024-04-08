package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    @Query("SELECT c.conversationId " +
            "FROM conversation c " +
            "JOIN group_member gm1 ON c.conversationId = gm1.conversation.conversationId AND gm1.userId = :userId1 " +
            "JOIN group_member gm2 ON c.conversationId = gm2.conversation.conversationId AND gm2.userId = :userId2 " +
            "WHERE c.isGroupConversation = false " +
            "GROUP BY c.conversationId " +
            "HAVING COUNT(DISTINCT gm1.userId) = 1 AND COUNT(DISTINCT gm2.userId) = 1")
    List<UUID> findPrivateConversationForTwoUsers(UUID userId1, UUID userId2);
}
