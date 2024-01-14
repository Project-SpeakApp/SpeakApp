package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.PostCreateDTO;
import com.speakapp.postservice.dtos.PostGetDTO;
import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.exceptions.ServiceLayerException;
import com.speakapp.postservice.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostGetDTO createPost(@Valid @RequestBody PostCreateDTO postCreateDTO, @RequestHeader("UserId") UUID userId) {
        return postService.createPost(postCreateDTO, userId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID postId, @RequestHeader("UserId") UUID userId) {
        postService.deletePost(userId, postId);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostGetDTO updatePost(@Valid @RequestBody PostCreateDTO postCreateDTO, @RequestHeader("UserId") UUID userId, @PathVariable UUID postId) {
        return postService.updatePost(postCreateDTO, postId, userId);

    }

    @GetMapping("/by-user/{userIdOfProfileOwner}")
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getUserLatestPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "5") int pageSize, @PathVariable UUID userIdOfProfileOwner, @RequestHeader("UserId") UUID userId ) {
        return postService.getUsersLatestPosts(pageNumber, pageSize, userIdOfProfileOwner, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getLatestPosts(@RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "5") int pageSize,  @RequestHeader("UserId") UUID userId ) {
      return postService.getLatestPosts(pageNumber, pageSize, userId);
    }
}
