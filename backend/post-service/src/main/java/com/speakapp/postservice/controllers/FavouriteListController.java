package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.FavouriteListAddPostDTO;
import com.speakapp.postservice.dtos.FavouriteListCreateDTO;
import com.speakapp.postservice.dtos.FavouriteListDeletePostDTO;
import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.entities.FavouriteList;
import com.speakapp.postservice.services.FavouriteListService;
import com.speakapp.postservice.utils.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/favouriteList")
public class FavouriteListController {

    private final FavouriteListService favouriteListService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostPageGetDTO getFavouriteList(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int firstPost,
            @RequestParam(defaultValue = "10") int lastPost,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return favouriteListService.getFavouritePosts(userId, firstPost, lastPost);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPostToFavouriteList(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FavouriteListAddPostDTO favouriteListAddPostDTO
    ) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        favouriteListService.addPost(userId, favouriteListAddPostDTO);
    }

    @DeleteMapping
    public void deletePostFromFavouriteList(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FavouriteListDeletePostDTO favouriteListDeletePostDTO
    ) {
        UUID userId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        favouriteListService.deletePost(userId, favouriteListDeletePostDTO);
    }
}
