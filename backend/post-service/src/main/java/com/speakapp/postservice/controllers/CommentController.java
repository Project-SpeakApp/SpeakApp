package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.CommentCreateDTO;
import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import com.speakapp.postservice.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CommentPageGetDTO getCommentsForPost(@RequestParam(defaultValue = "0") int pageNumber,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam UUID postId,
                                                @RequestHeader("UserId") UUID userId ){
        return commentService.getCommentsForPost(pageNumber, pageSize, postId, userId);
    }

    @GetMapping("/by-created-at")
    public CommentPageGetDTO getCommentsForPostByCreatedAtSorted(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String sort,
            @RequestParam UUID postId,
            @RequestHeader("UserId") UUID userId) {

        return commentService.getCommentsForPostByCreatedAt(
                pageNumber, pageSize, postId, userId, sort);
    }

    @GetMapping("/by-reactions")
    public CommentPageGetDTO getCommentsForPostByReactionsSorted(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String sort,
            @RequestParam UUID postId,
            @RequestHeader("UserId") UUID userId) {

        return commentService.getCommentsForPostByReactions(
                pageNumber, pageSize, postId, userId, sort);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentGetDTO createComment(@RequestBody CommentCreateDTO commentCreateDTO, @RequestHeader("UserId") UUID userId) {
        return commentService.createComment(commentCreateDTO, userId);
    }
}
