package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import com.chatservice.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
  Page<Message> findAllByConversationOrderByDeliveredAtDesc(Conversation conversation, Pageable pageable);
}
