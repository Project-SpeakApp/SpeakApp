package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import com.chatservice.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query(value = "SELECT m.* FROM message m " +
            "INNER JOIN (" +
            "    SELECT m1.conversation_id AS conv_join_id, MAX(m1.sent_at) AS maxSentAt " +
            "    FROM message m1 " +
            "    INNER JOIN group_member gm ON m1.from_user_id = gm.user_id AND m1.conversation_id = gm.conversation_id " +
            "    GROUP BY m1.conversation_id" +
            ") maxDate " +
            "ON maxDate.conv_join_id = m.conversation_id " +
            "AND maxDate.maxSentAt = m.sent_at " +
            "INNER JOIN (" +
            "    SELECT gr.conversation_id AS user_conv_id FROM group_member gr WHERE gr.user_id = :userId" +
            ") userConv " +
            "ON userConv.user_conv_id = m.conversation_id " +
            "ORDER BY maxDate.maxSentAt DESC", nativeQuery = true)
    Page<Message> findLatestMessageForUserConversations(UUID userId, Pageable pageable);


  Page<Message> findAllByConversationOrderByDeliveredAtDesc(Conversation conversation, Pageable pageable);
}
