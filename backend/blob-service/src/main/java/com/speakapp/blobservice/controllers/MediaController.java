package com.speakapp.blobservice.controllers;

import com.speakapp.blobservice.dtos.BlobFileDTO;
import com.speakapp.blobservice.dtos.BlobMetadataDTO;
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
        public ResponseEntity<byte[]> downloadMedia(@PathVariable UUID mediaId) {
            BlobFileDTO file = mediaService.downloadMediaByUUID(mediaId);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file.getContent());
        }
        @PostMapping("/upload")
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<String> uploadMedia(@RequestParam TypeMedia type, @RequestParam("file") MultipartFile file, @RequestHeader("UserId") UUID userId) throws IOException {
            if (file.isEmpty()) {

                return new ResponseEntity<String>("No file has been uploaded", HttpStatus.NOT_FOUND);
            }
            BlobMetadataDTO blobMetadataDTO = mediaService.uploadMedia(file, type, userId);
            String mediaId = blobMetadataDTO.getMediaId();
            return ResponseEntity.ok(mediaId);

        }




}
