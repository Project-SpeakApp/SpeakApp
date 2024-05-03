package com.chatservice.controllers;

import com.chatservice.services.InternalChatService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/chat")
@RequiredArgsConstructor
public class InternalChatController {


  private final InternalChatService internalChatService;

  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateGroupMemberFirstAndLastName(@PathVariable UUID userId, @RequestParam String updatedFirstName,
      @RequestParam String updatedLastName) {
    internalChatService.updateGroupMemberData(userId, updatedFirstName, updatedLastName);
  }
}
