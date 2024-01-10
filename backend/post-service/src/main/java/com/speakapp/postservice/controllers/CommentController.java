package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.CommentCreateDTO;
import com.speakapp.postservice.dtos.CommentGetDTO;
import com.speakapp.postservice.dtos.CommentPageGetDTO;
import com.speakapp.postservice.dtos.CommentUpdateDTO;
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


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentGetDTO createComment(@RequestBody CommentCreateDTO commentCreateDTO, @RequestHeader("UserId") UUID userId) {
        return commentService.createComment(commentCreateDTO, userId);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentGetDTO updateComment(@RequestBody CommentUpdateDTO commentUpdateDTO, @RequestHeader("UserId") UUID userId, @PathVariable UUID commentId) {
        return commentService.updateComment(commentUpdateDTO, commentId, userId);
    }
}
