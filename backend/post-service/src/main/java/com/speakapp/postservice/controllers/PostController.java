package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.dtos.PostCreateDTO;
import com.speakapp.postservice.dtos.PostGetDTO;
import com.speakapp.postservice.services.PostService;
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
    public PostGetDTO createPost(@RequestBody PostCreateDTO postCreateDTO, @RequestHeader("UserId") UUID userId) {
        return postService.createPost(postCreateDTO, userId);
    }

    @GetMapping("/{userIdOfProfileOwner}")
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getUserLatestPosts(@RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "5") int pageSize, @PathVariable UUID userIdOfProfileOwner, @RequestHeader("UserId") UUID userId ){
        return postService.getUsersLatestPosts(pageNumber, pageSize, userIdOfProfileOwner, userId);
    }

}
