package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

  Optional<Conversation> findByConversationId(UUID conversationID);

}
