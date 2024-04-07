package com.chatservice.services;

import com.chatservice.communication.UserServiceCommunicationClient;
import com.chatservice.dtos.*;
import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import com.chatservice.entities.Message;
import com.chatservice.mappers.ConversationMapper;
import com.chatservice.mappers.GroupMemberMapper;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;

    private final GroupMemberRepository groupMemberRepository;

    private final ConversationMapper conversationMapper;

    private final GroupMemberMapper groupMemberMapper;

    private final UserServiceCommunicationClient userServiceCommunicationClient;

    private final MessageRepository messageRepository;

    public Conversation createPrivateConversation(NewPrivateConversationDTO newPrivateConversationDTO,
                                                  UUID conversationCreatorUser){

        if(newPrivateConversationDTO.getConversationMemberUser().equals(conversationCreatorUser)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId's must be different to create a conversation");
        }

        List<GroupMember> usersGroupMembership = groupMemberRepository.findGroupMemberByUserIdsAndConversationIsPrivate(
                conversationCreatorUser,
                newPrivateConversationDTO.getConversationMemberUser()
        );

        if(!usersGroupMembership.isEmpty()) {
            List<UUID> uniqueConversationsIds = new ArrayList<>();

            for(GroupMember groupMember : usersGroupMembership){
                UUID conversationIdToCheck = groupMember.getConversation().getConversationId();

                if(!uniqueConversationsIds.contains(conversationIdToCheck)){
                    uniqueConversationsIds.add(conversationIdToCheck);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Conversation with user: " + newPrivateConversationDTO.getConversationMemberUser() + " already exists");
                }
            }
        }

        Conversation createdConversation = conversationRepository.save(conversationMapper.toEntity(false));

        groupMemberRepository.save(groupMemberMapper.toEntity(conversationCreatorUser, createdConversation));

        groupMemberRepository.save(groupMemberMapper.toEntity(newPrivateConversationDTO.getConversationMemberUser(),
                createdConversation));

        return createdConversation;

    }

    public ChatPreviewPageDTO getChatPreviews(UUID userId, int pageNumber, int pageSize){
        List<UUID> conversationsOfCurrentUser = groupMemberRepository.findConversationsByGroupMember(userId);
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Message> messagesPage = messageRepository.findLatestMessageForEachConversation(conversationsOfCurrentUser, page);

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


        UserGetDTO messageAuthor = userServiceCommunicationClient.getUserById(message.getFromUser());

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
