package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.CommentCreateDTO;
import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import com.speakapp.postservice.dtos.CommentUpdateDTO;
import com.speakapp.postservice.services.CommentService;
import com.speakapp.postservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtDecoder jwtDecoder;
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    @GetMapping("")
    public CommentPageGetDTO getCommentsForPostByCreatedAtSorted(
            @RequestParam(defaultValue = "0") int firstComment,
            @RequestParam(defaultValue = "10") int lastComment,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam UUID postId,
            @RequestHeader("Authorization") String authHeader) {

        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);
        return commentService.getCommentsForPost(
                firstComment, lastComment, postId, userId, sortBy, sortDirection);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentGetDTO createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO,
                                       @RequestHeader("Authorization") String authHeader) {
        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);
        return commentService.createComment(commentCreateDTO, userId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable UUID commentId, @RequestHeader("Authorization") String authHeader) {
        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);
        commentService.deleteComment(userId, commentId);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentGetDTO updateComment(@RequestBody @Valid CommentUpdateDTO commentUpdateDTO,
                                       @RequestHeader("Authorization") String authHeader,
                                       @PathVariable UUID commentId) {
        String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
        UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);
        return commentService.updateComment(commentUpdateDTO, commentId, userId);
    }
}
