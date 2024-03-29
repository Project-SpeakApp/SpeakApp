package com.chatservice.services;

import com.chatservice.dtos.MessageDTO;
import com.chatservice.dtos.NewPrivateConversationDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import com.chatservice.entities.Message;
import com.chatservice.entities.MessageType;
import com.chatservice.exceptions.AccessDeniedException;
import com.chatservice.exceptions.ConversationNotFound;
import com.chatservice.mappers.ConversationMapper;
import com.chatservice.mappers.MessageMapper;
import com.chatservice.repositories.ConversationRepository;
import com.chatservice.repositories.GroupMemberRepository;
import com.chatservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ConversationRepository conversationRepository;

  private final GroupMemberRepository groupMemberRepository;

  private final MessageRepository messageRepository;

  private final ConversationMapper conversationMapper;

  private final MessageMapper messageMapper;

  public Conversation createPrivateConversation(NewPrivateConversationDTO newPrivateConversationDTO,
      UUID conversationCreatorUser) {

    List<GroupMember> usersGroupMembership = groupMemberRepository.findGroupMemberByUserIdsAndConversationIsPrivate(
        conversationCreatorUser,
        newPrivateConversationDTO.getConversationMemberUser()
    );

    if (!usersGroupMembership.isEmpty()) {
      List<UUID> uniqueConversationsIds = new ArrayList<>();

      for (GroupMember groupMember : usersGroupMembership) {
        UUID conversationIdToCheck = groupMember.getConversation().getConversationId();

        if (!uniqueConversationsIds.contains(conversationIdToCheck)) {
          uniqueConversationsIds.add(conversationIdToCheck);
        } else {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Conversation with user: " + newPrivateConversationDTO.getConversationMemberUser()
                  + " already exists");
        }
      }
    }

    Conversation createdConversation = conversationRepository.save(
        conversationMapper.toEntity(false));

    groupMemberRepository.save(GroupMember.builder()
        .conversation(createdConversation)
        .userId(conversationCreatorUser)
        .build());

    groupMemberRepository.save(GroupMember.builder()
        .conversation(createdConversation)
        .userId(newPrivateConversationDTO.getConversationMemberUser())
        .build());

    return createdConversation;

  }

  public List<MessageDTO> getConversationHistory(int pageNumber, int pageSize, UUID conversationId,
      UUID userId) {
    Conversation conversation = conversationRepository.findByConversationId(conversationId)
        .orElseThrow(ConversationNotFound::new);

    if (!groupMemberRepository.existsGroupMemberByConversationAndAndUserId(conversation, userId)) {
      throw new AccessDeniedException();
    }

    Pageable page = PageRequest.of(pageNumber, pageSize);
    Page<Message> messagesPage = messageRepository.findAllByConversationOrderByDeliveredAtDesc(conversation, page);

    return messagesPage.getContent().stream().map(message -> {
      return MessageDTO.builder()
          .fromUser(message.getFromUser())
          .toUser(null)  //is it even useful?
          .content(message.getContent())
          .type(MessageType.valueOf(message.getType())) // maybe just return type as string and store as MessageType?
          .conversationId(message.getConversation().getConversationId())
          .build();
    }).toList();
  }

}
