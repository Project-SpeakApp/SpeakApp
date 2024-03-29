package com.chatservice.mappers;

import com.chatservice.entities.Conversation;
import com.chatservice.entities.GroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface GroupMemberMapper {
    @Mapping(target = "groupMemberId", ignore = true)
    @Mapping(target = "leftDatetime", ignore = true)
    @Mapping(target = "joinedDatetime", ignore = true)
    GroupMember toEntity(UUID userId, Conversation conversation);
}
