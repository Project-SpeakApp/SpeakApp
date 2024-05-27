package com.chatservice.services;

import com.chatservice.communication.UserServiceCommunicationClient;
import com.chatservice.dtos.*;
import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import com.chatservice.entities.Message;
import com.chatservice.entities.MessageType;
import com.chatservice.exceptions.AccessDeniedException;
import com.chatservice.exceptions.BadRequestException;
import com.chatservice.exceptions.ConversationNotFoundException;
import com.chatservice.exceptions.MessageNotFoundException;
import com.chatservice.repositories.ConversationRepository;
import com.chatservice.repositories.GroupMemberRepository;
import com.chatservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                                                  UUID conversationCreatorUser){

        if(newPrivateConversationDTO.getConversationMemberUser().equals(conversationCreatorUser)){
            throw new BadRequestException("Bad request: UserId's must be different to create a conversation");
        }

        List<UUID> checkIfPrivateConversationExists = conversationRepository.findConversationsForTwoUsers
                (newPrivateConversationDTO.getConversationMemberUser(),
                conversationCreatorUser);

        if(!checkIfPrivateConversationExists.isEmpty()){
            throw new BadRequestException(
                    "Bad request: Conversation with user: " + newPrivateConversationDTO.getConversationMemberUser()
                            + " already exists");
        }

        Conversation createdConversation = conversationRepository.save(Conversation.builder()
                .isGroupConversation(false).build());

        groupMemberRepository.save(GroupMember.builder()
                .userId(conversationCreatorUser)
                .conversation(createdConversation)
                .build());

        groupMemberRepository.save(GroupMember.builder()
                .userId(newPrivateConversationDTO.getConversationMemberUser())
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
                .fromUser(messageAuthor)
                .conversationId(message.getConversation().getConversationId())
                .sentAt(message.getSentAt()).build();

        List<UUID> conversationMembers = groupMemberRepository.findUserIdsByConversation(getConversationForMessage.getConversationId());
        List<UserGetDTO> conversationMembersDTO = conversationMembers.stream().map(userServiceCommunicationClient::getUserById).toList();

        return ChatPreviewDTO.builder()
                .ChatMembers(conversationMembersDTO)
                .lastMessage(messageGetDTO)
                .conversationGetDTO(conversationGetDTO).build();
    }

  public ConversationHistoryGetDTO getConversationHistory(int pageNumber, int pageSize, UUID conversationId,
                                                          UUID userId) {
    Conversation conversation = conversationRepository.findByConversationId(conversationId)
        .orElseThrow(ConversationNotFoundException::new);

    if (!groupMemberRepository.existsGroupMemberByConversationAndAndUserId(conversation, userId)) {
      throw new AccessDeniedException();
    }

    Sort sort = Sort.by(Sort.Direction.DESC, "sentAt");
    Pageable page = PageRequest.of(pageNumber, pageSize, sort);
    Page<Message> messagesPage = messageRepository.findAllByConversationOrderByDeliveredAtDesc(conversation, page);

    List<MessageGetDTO> listOfMessages = messagesPage.getContent().stream().map(message -> MessageGetDTO.builder()
        .fromUser(userServiceCommunicationClient.getUserById(message.getFromUserId()))
        .content(message.getContent())
        .type(MessageType.valueOf(message.getType().toString()))
        .conversationId(message.getConversation().getConversationId())
        .sentAt(message.getSentAt())
        .build()).toList();

    return ConversationHistoryGetDTO.builder()
            .listOfMessages(listOfMessages)
            .listOfMessages(listOfMessages)
            .currentPage(page.getPageNumber())
            .totalPages(messagesPage.getTotalPages()).build();
  }

  public Message saveMessage(MessagePrivateCreateDTO messagePrivateCreateDTO){
    Conversation conversation = conversationRepository.findByConversationId(messagePrivateCreateDTO.getConversationId())
        .orElseThrow(ConversationNotFoundException::new);

    return messageRepository.save(Message.builder()
            .fromUserId(messagePrivateCreateDTO.getFromUserId())
            .content(messagePrivateCreateDTO.getContent())
            .type(messagePrivateCreateDTO.getType())
            .conversation(conversation)
            .responseToMessage(null)
        .build());
  }

<<<<<<< HEAD
  public MessageGetDTO convertMessageToGetDTO(Message message){
        conversationRepository.findByConversationId(message.getConversation().getConversationId())
              .orElseThrow(ConversationNotFound::new);

      UserGetDTO messageAuthor = userServiceCommunicationClient.getUserById(message.getFromUserId());
      return MessageGetDTO.builder()
              .sentAt(message.getSentAt())
              .conversationId(message.getConversation().getConversationId())
              .content(message.getContent())
              .fromUser(messageAuthor)
              .type(message.getType())
              .build();
=======
  public void deleteMessage(UUID messageId, UUID fromUserId){
        Message messageToDelete = messageRepository.findByMessageIdAndFromUserId(messageId, fromUserId)
                .orElseThrow(MessageNotFoundException::new);

        if(messageToDelete.isDeleted()){
            throw new BadRequestException("Message is already deleted!");
        }
        else{
            messageToDelete.setDeleted(true);
        }

        messageRepository.save(messageToDelete);
>>>>>>> main
  }

}
