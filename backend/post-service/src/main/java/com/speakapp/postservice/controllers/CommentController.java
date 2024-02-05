package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.CommentCreateDTO;
import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import com.speakapp.postservice.dtos.CommentUpdateDTO;
import com.speakapp.postservice.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @GetMapping("")
    public CommentPageGetDTO getCommentsForPostByCreatedAtSorted(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam UUID postId,
            @RequestHeader("UserId") UUID userId) {

        return commentService.getCommentsForPost(
                pageNumber, pageSize, postId, userId, sortBy, sortDirection);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentGetDTO createComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO, @RequestHeader("UserId") UUID userId) {
        return commentService.createComment(commentCreateDTO, userId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable UUID commentId, @RequestHeader("UserId") UUID userId) {
        commentService.deleteComment(userId, commentId);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentGetDTO updateComment(@RequestBody @Valid CommentUpdateDTO commentUpdateDTO, @RequestHeader("UserId") UUID userId, @PathVariable UUID commentId) {
        return commentService.updateComment(commentUpdateDTO, commentId, userId);
    }
}
