package com.speakapp.postservice.controllers;

import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.exceptions.ServiceLayerException;
import com.speakapp.postservice.services.ReactionService;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/reactions")
@RequiredArgsConstructor
public class ReactionController {

  private final ReactionService reactionService;

  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ReactionType createUpdatePostReaction(@RequestParam(required = false) ReactionType reactionType, @PathVariable UUID postId,
                                               @RequestHeader("UserId") UUID userId) throws ServiceLayerException {
    return reactionService.createUpdatePostReaction(reactionType, postId, userId);
  }

}
