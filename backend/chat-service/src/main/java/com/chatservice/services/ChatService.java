package com.chatservice.services;

import com.chatservice.communication.UserServiceCommunicationClient;
import com.chatservice.dtos.ChatPreviewDTO;
import com.chatservice.dtos.ChatPreviewPageDTO;
import com.chatservice.dtos.ConversationGetDTO;
import com.chatservice.dtos.MessageGetDTO;
import com.chatservice.dtos.MessagePrivateCreateDTO;
import com.chatservice.dtos.NewPrivateConversationDTO;
import com.chatservice.dtos.UserGetDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import com.chatservice.entities.Message;
import com.chatservice.entities.MessageType;
import com.chatservice.exceptions.AccessDeniedException;
import com.chatservice.exceptions.BadRequestException;
import com.chatservice.exceptions.ConversationNotFound;
import com.chatservice.repositories.ConversationRepository;
import com.chatservice.repositories.GroupMemberRepository;
import com.chatservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;

    private final GroupMemberRepository groupMemberRepository;

    private final UserServiceCommunicationClient userServiceCommunicationClient;

    private final MessageRepository messageRepository;

    public Conversation createPrivateConversation(NewPrivateConversationDTO newPrivateConversationDTO,
                                                  UUID conversationCreatorUserId){

        if(newPrivateConversationDTO.getConversationMemberUserId().equals(conversationCreatorUserId)){
            throw new BadRequestException("Bad request: UserId's must be different to create a conversation");
        }

        List<UUID> checkIfPrivateConversationExists = conversationRepository.findConversationsForTwoUsers
                (newPrivateConversationDTO.getConversationMemberUserId(),
                conversationCreatorUserId);

        if(!checkIfPrivateConversationExists.isEmpty()){
            throw new BadRequestException(
                    "Bad request: Conversation with user: " + newPrivateConversationDTO.getConversationMemberUserId()
                            + " already exists");
        }

        Conversation createdConversation = conversationRepository.save(Conversation.builder()
                .isGroupConversation(false).build());

      UserGetDTO conversationCreatorUser = userServiceCommunicationClient.getUserById(conversationCreatorUserId);
      String[] fullNamePartsCreatorUser = conversationCreatorUser.getFullName().trim().split("\\s+", 2);

      UserGetDTO conversationMemberUser = userServiceCommunicationClient.getUserById(newPrivateConversationDTO.getConversationMemberUserId());
      String[] fullNamePartsMemberUser = conversationMemberUser.getFullName().trim().split("\\s+", 2);

        groupMemberRepository.save(GroupMember.builder()
                .userId(conversationCreatorUserId)
                .firstName(fullNamePartsCreatorUser[0])
                .lastName(fullNamePartsCreatorUser[1])
                .conversation(createdConversation)
                .build());

        groupMemberRepository.save(GroupMember.builder()
                .userId(newPrivateConversationDTO.getConversationMemberUserId())
                .firstName(fullNamePartsMemberUser[0])
                .lastName(fullNamePartsMemberUser[1])
                .conversation(createdConversation)
                .build());

        return createdConversation;

    }

    public ChatPreviewPageDTO getChatPreviews(UUID userId, int pageNumber, int pageSize){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Message> messagesPage = messageRepository.findLatestMessageForUserConversations(userId, page);

        return createChatPreviewPageDTOFromMessagesPage(messagesPage, page);
    }

    private ChatPreviewPageDTO createChatPreviewPageDTOFromMessagesPage(Page<Message> messagesPage, Pageable page){
        List<ChatPreviewDTO> chatPreviewDTOList = messagesPage.getContent().stream().map(this::createChatPreviewDTO).toList();

        return ChatPreviewPageDTO.builder()
                .chatPreviewDTOS(chatPreviewDTOList)
                .totalPages(messagesPage.getTotalPages())
                .pageSize(page.getPageSize())
                .currentPage(page.getPageNumber()).build();
    }

    private ChatPreviewDTO createChatPreviewDTO(Message message){
        Conversation getConversationForMessage = message.getConversation();

        ConversationGetDTO conversationGetDTO = ConversationGetDTO.builder()
                .conversationId(getConversationForMessage.getConversationId())
                .conversationName(getConversationForMessage.getConversationName())
                .isGroupConversation(getConversationForMessage.isGroupConversation())
                .build();


        UserGetDTO messageAuthor = userServiceCommunicationClient.getUserById(message.getFromUserId());

        MessageGetDTO messageGetDTO = MessageGetDTO.builder()
                .content(message.getContent())
                .type(message.getType())
                .fromUser(messageAuthor).build();

        List<UUID> conversationMembers = groupMemberRepository.findUserIdsByConversation(getConversationForMessage.getConversationId());
        List<UserGetDTO> conversationMembersDTO = conversationMembers.stream().map(userServiceCommunicationClient::getUserById).toList();

        return ChatPreviewDTO.builder()
                .ChatMembers(conversationMembersDTO)
                .lastMessage(messageGetDTO)
                .conversationGetDTO(conversationGetDTO).build();
    }

  public List<MessageGetDTO> getConversationHistory(int pageNumber, int pageSize, UUID conversationId,
      UUID userId) {
    Conversation conversation = conversationRepository.findByConversationId(conversationId)
        .orElseThrow(ConversationNotFound::new);

    if (!groupMemberRepository.existsGroupMemberByConversationAndAndUserId(conversation, userId)) {
      throw new AccessDeniedException();
    }

    Pageable page = PageRequest.of(pageNumber, pageSize);
    Page<Message> messagesPage = messageRepository.findAllByConversationOrderByDeliveredAtDesc(conversation, page);

    return messagesPage.getContent().stream().map(message -> {
      return MessageGetDTO.builder()
          .fromUser(userServiceCommunicationClient.getUserById(message.getFromUserId()))
          .content(message.getContent())
          .type(MessageType.valueOf(message.getType().toString()))
          .build();
    }).toList();
  }

  public void saveMessage(MessagePrivateCreateDTO messagePrivateCreateDTO){
    Conversation conversation = conversationRepository.findByConversationId(messagePrivateCreateDTO.getConversationId())
        .orElseThrow(ConversationNotFound::new);

    messageRepository.save(Message.builder()
            .fromUserId(messagePrivateCreateDTO.getFromUserId())
            .content(messagePrivateCreateDTO.getContent())
            .type(messagePrivateCreateDTO.getType())
            .conversation(conversation)
            .responseToMessage(null)
        .build());
  }

}
