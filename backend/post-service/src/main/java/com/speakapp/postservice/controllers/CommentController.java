package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.CommentCreateDTO;
import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import com.speakapp.postservice.exceptions.ServiceLayerException;
import com.speakapp.postservice.services.CommentService;
import jakarta.validation.Valid;
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
                                                @RequestHeader("UserId") UUID userId ) throws ServiceLayerException {
        return commentService.getCommentsForPost(pageNumber, pageSize, postId, userId);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentGetDTO createComment(@Valid @RequestBody CommentCreateDTO commentCreateDTO, @RequestHeader("UserId") UUID userId) throws ServiceLayerException {
        return commentService.createComment(commentCreateDTO, userId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable UUID commentId, @RequestHeader("UserId") UUID userId) throws ServiceLayerException {
        commentService.deleteComment(userId, commentId);
    }
}
