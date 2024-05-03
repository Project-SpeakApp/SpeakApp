package com.chatservice.services;

import com.chatservice.entities.GroupMember;
import com.chatservice.exceptions.UserNotFoundException;
import com.chatservice.repositories.GroupMemberRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalChatService {

  private final GroupMemberRepository groupMemberRepository;
  public void updateGroupMemberData(UUID userId, String updatedFirstName, String updatedLastName){
    GroupMember groupMember = groupMemberRepository.findGroupMemberByUserId(userId)
        .orElseThrow(() -> new UserNotFoundException("Group member instance for user not found, data integrity violation!"));

    groupMember.setFirstName(updatedFirstName);
    groupMember.setLastName(updatedLastName);
    groupMemberRepository.save(groupMember);
  }
}
