package com.speakapp.postservice.controllers;

import com.speakapp.postservice.entities.ReactionType;
import com.speakapp.postservice.services.ReactionService;


import java.util.UUID;

import com.speakapp.postservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PutMapping("/posts/reactions/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReactionType createUpdatePostReaction(@RequestParam(required = false) ReactionType reactionType,
                                                 @PathVariable UUID postId,
                                                 @RequestHeader("Authorization") String authHeader){
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return reactionService.createUpdatePostReaction(reactionType, postId, userId);
    }

    @PutMapping("/comments/reactions/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReactionType createUpdateCommentReaction(@RequestParam(required = false) ReactionType reactionType,
                                                    @PathVariable UUID commentId,
                                                    @RequestHeader("Authorization") String authHeader){
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return reactionService.createUpdateCommentReaction(reactionType, commentId, userId);
    }

}
