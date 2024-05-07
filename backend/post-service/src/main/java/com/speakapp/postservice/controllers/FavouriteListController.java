package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.PostPageGetDTO;
import com.speakapp.postservice.services.FavouriteListService;
import com.speakapp.postservice.utils.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/favouriteList")
public class FavouriteListController {

    private final FavouriteListService favouriteListService;

    @GetMapping
    public PostPageGetDTO getFavouriteList(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int firstPost,
            @RequestParam(defaultValue = "10") int lastPost,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        UUID requesterId = JwtDecoder.extractUserIdFromAuthorizationHeader(authHeader);
        return favouriteListService.getFavouritePosts(requesterId, firstPost, lastPost);
    }

}
