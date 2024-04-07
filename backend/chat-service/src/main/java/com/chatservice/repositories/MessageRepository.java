package com.chatservice.repositories;

import com.chatservice.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query(value = "SELECT * FROM Message m " +
            "INNER JOIN (" +
            "    SELECT m1.conversation_id AS conversationId, MAX(m1.sent_at) AS maxSentAt " +
            "    FROM Message m1 " +
            "    GROUP BY m1.conversation_id" +
            ") maxDate " +
            "ON maxDate.conversationId = m.conversation_id " +
            "AND maxDate.maxSentAt = m.sent_at " +
            "WHERE m.conversation_id IN :conversationIds " +
            "ORDER BY m.sent_at DESC", nativeQuery = true)
    Page<Message> findLatestMessageForEachConversation(List<UUID> conversationIds, Pageable pageable);

}
