package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import com.chatservice.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query(value = "SELECT * FROM message m " +
            "INNER JOIN (" +
            "    SELECT m1.conversation_id AS conversationId, MAX(m1.sent_at) AS maxSentAt " +
            "    FROM message m1 " +
            "    INNER JOIN group_member gm ON m1.conversation_id = gm.conversation_id " +
            "    WHERE gm.user_id = :userId " +
            "    GROUP BY m1.conversation_id" +
            ") maxDate " +
            "ON maxDate.conversationId = m.conversation_id " +
            "AND maxDate.maxSentAt = m.sent_at " +
            "ORDER BY maxDate.maxSentAt DESC", nativeQuery = true)
    Page<Message> findLatestMessageForUserConversations(UUID userId, Pageable pageable);

    Page<Message> findAllByConversationOrderByDeliveredAtDesc(Conversation conversation, Pageable pageable);

    Optional<Message> findByMessageIdAndFromUserId(UUID messageId, UUID fromUserId);
}
