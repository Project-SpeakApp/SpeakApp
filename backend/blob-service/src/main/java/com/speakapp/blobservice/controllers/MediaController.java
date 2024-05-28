package com.speakapp.blobservice.controllers;

import com.speakapp.blobservice.dtos.BlobFileDTO;
import com.speakapp.blobservice.dtos.BlobMetadataDTO;
import com.speakapp.blobservice.entities.TypeMedia;
import com.speakapp.blobservice.services.MediaService;
import com.speakapp.blobservice.utils.JwtDecoder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class MediaController {

        private final MediaService mediaService;
        private final JwtDecoder jwtDecoder;
        private static final String AUTH_HEADER_PREFIX = "Bearer ";
        @GetMapping("/{mediaId}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<byte[]> downloadMedia(@PathVariable UUID mediaId) {
            BlobFileDTO file = mediaService.downloadMediaByUUID(mediaId);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file.getContent());
        }
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<String> uploadMedia(@RequestParam TypeMedia type,
                                                  @RequestBody MultipartFile file,
                                                  @RequestHeader("Authorization") String authHeader) throws IOException {
            String jwtToken = authHeader.replace(AUTH_HEADER_PREFIX, "");
            UUID userId = jwtDecoder.extractUserIdFromJwt(jwtToken);
            BlobMetadataDTO blobMetadataDTO = mediaService.uploadMedia(file, type, userId);
            return ResponseEntity.ok(blobMetadataDTO.getMediaId());
        }
}
