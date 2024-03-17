package com.speakapp.blobservice.controllers;

import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.services.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

        private final MediaService mediaService;
        @GetMapping("/{mediaId}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<byte[]> downloadMedia(@RequestParam TypeMedia typeMedia, @PathVariable UUID mediaId) throws IOException {
            byte [] content = mediaService.downloadMediaByUUID(mediaId, typeMedia);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(content);
        }
        @PostMapping("/upload")
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<String> uploadMedia(@RequestParam TypeMedia type, @RequestBody MultipartFile file, @RequestHeader("UserId") UUID userId) throws IOException {
            String blobUrl = mediaService.uploadMedia(file, type, userId);
            return ResponseEntity.ok(blobUrl);
        }




}
