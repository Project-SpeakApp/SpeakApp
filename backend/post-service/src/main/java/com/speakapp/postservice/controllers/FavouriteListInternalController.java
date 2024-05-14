package com.speakapp.postservice.controllers;

import com.speakapp.postservice.dtos.FavouriteListCreateDTO;
import com.speakapp.postservice.services.FavouriteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/internal/posts/favouriteList")
@RequiredArgsConstructor
public class FavouriteListInternalController {

    private final FavouriteListService favouriteListService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createFavouriteList(@RequestBody FavouriteListCreateDTO favouriteListCreateDTO) {
        favouriteListService.createFavouriteList(favouriteListCreateDTO);
    }
}
