package com.chatservice.services;

import com.chatservice.dtos.NewPrivateConversationDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import com.chatservice.mappers.ConversationMapper;
import com.chatservice.repositories.ConversationRepository;
import com.chatservice.repositories.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
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

    private final ConversationMapper conversationMapper;

    public Conversation createPrivateConversation(NewPrivateConversationDTO newPrivateConversationDTO){

        List<GroupMember> usersGroupMembership = groupMemberRepository.findGroupMemberByUserIdsAndConversationIsPrivate(
                newPrivateConversationDTO.getConversationCreatorUser(),
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

        return conversationRepository.save(conversationMapper.toEntity(false));

    }

}
