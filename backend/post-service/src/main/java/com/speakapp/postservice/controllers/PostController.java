package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.PostCreateDTO;
import com.speakapp.postservice.dtos.PostGetDTO;
import com.speakapp.postservice.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostGetDTO createPost(@RequestBody PostCreateDTO postCreateDTO, @RequestHeader("UserId") UUID userId) {
        log.info("Creating post for user with id = {} and post body = {}", userId, postCreateDTO);
        return postService.createPost(postCreateDTO, userId);
    }

}
