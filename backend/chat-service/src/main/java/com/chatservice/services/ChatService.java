package com.chatservice.services;

import com.chatservice.communication.UserServiceCommunicationClient;
import com.chatservice.dtos.*;
import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import com.chatservice.entities.Message;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId's must be different to create a conversation");
        }

        List<UUID> checkIfPrivateConversationExists = conversationRepository.findConversationsForTwoUsers
                (newPrivateConversationDTO.getConversationMemberUser(),
                conversationCreatorUser);

        if(!checkIfPrivateConversationExists.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Conversation with user: " + newPrivateConversationDTO.getConversationMemberUser() + " already exists");
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
                .fromUser(messageAuthor).build();

        List<UUID> conversationMembers = groupMemberRepository.findUserIdsByConversation(getConversationForMessage.getConversationId());
        List<UserGetDTO> conversationMembersDTO = conversationMembers.stream().map(userServiceCommunicationClient::getUserById).toList();

        return ChatPreviewDTO.builder()
                .ChatMembers(conversationMembersDTO)
                .lastMessage(messageGetDTO)
                .conversationGetDTO(conversationGetDTO).build();
    }

}
