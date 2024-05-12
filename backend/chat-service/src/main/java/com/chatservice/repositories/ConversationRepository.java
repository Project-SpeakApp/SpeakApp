package com.chatservice.repositories;

import com.chatservice.entities.Conversation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  Optional<Conversation> findByConversationId(UUID conversationID);

  @Query("SELECT gm.conversation " +
      "FROM group_member gm " +
      "JOIN gm.conversation gc " +
      "JOIN group_member gm2 ON gm.conversation = gm2.conversation " +
      "WHERE gm.userId = :userId1 " +
      "AND gc.isGroupConversation = false " +
      "AND ((LOWER(gm2.firstName) = LOWER(:firstName) AND LOWER(gm2.lastName) = LOWER(:lastName)) " +
      "OR LOWER(gc.conversationName) = LOWER(:conversationName))")
  Page<Conversation> findUserConversationsByOtherUserFullNameOrConversationName(UUID userId1, String firstName, String lastName, String conversationName, Pageable page);


  @Query("SELECT gm.conversation " +
      "FROM group_member gm JOIN gm.conversation gc, group_member gm2 " +
      "WHERE gm.userId = :userId1 " +
      "AND gc.isGroupConversation = false " +
      "AND gm.conversation = gm2.conversation " +
      "AND (LOWER(gm2.firstName) = LOWER(:firstName) OR LOWER(gc.conversationName) = LOWER(:conversationName))")
  Page<Conversation> findUserConversationsByOtherUserFirstNameOrConversationName(UUID userId1, String firstName, String conversationName, Pageable page);

  @Query("SELECT gm.conversation FROM group_member gm WHERE gm.userId = :userId")
  Page<Conversation> findAllUserConversations(UUID userId, Pageable page);

}
