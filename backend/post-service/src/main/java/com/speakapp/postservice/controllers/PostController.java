package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.PostCreateDTO;
import com.speakapp.postservice.dtos.PostGetDTO;
import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.services.PostService;
import com.speakapp.postservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostGetDTO createPost(@Valid @RequestBody PostCreateDTO postCreateDTO,
                                 @RequestHeader("Authorization") String authHeader) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return postService.createPost(postCreateDTO, userId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID postId, @RequestHeader("Authorization") String authHeader) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        postService.deletePost(userId, postId);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostGetDTO updatePost(@Valid @RequestBody PostCreateDTO postCreateDTO,
                                 @RequestHeader("Authorization") String authHeader,
                                 @PathVariable UUID postId) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return postService.updatePost(postCreateDTO, postId, userId);

    }

    @GetMapping("/by-user/{userIdOfProfileOwner}")
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getUserLatestPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             @PathVariable UUID userIdOfProfileOwner,
                                             @RequestHeader("Authorization") String authHeader) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return postService.getUsersLatestPosts(pageNumber, pageSize, userIdOfProfileOwner, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getLatestPosts(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "5") int pageSize,
                                         @RequestHeader("Authorization") String authHeader ) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return postService.getLatestPosts(pageNumber, pageSize, userId);
    }

    @GetMapping("/by-friends")
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getLatestPostsByFriends(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "5") int pageSize,
                                                  @RequestHeader("Authorization") String authHeader) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return postService.getLatestPostsByFriends(pageNumber, pageSize, userId);
    }

}
