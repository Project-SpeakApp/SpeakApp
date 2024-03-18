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
        public ResponseEntity<String> uploadMedia(@RequestParam TypeMedia type,
                                                  @RequestBody MultipartFile file,
                                                  @RequestHeader("UserId") UUID userId) throws IOException {
            if (file.isEmpty()) {

                return new ResponseEntity<>("No file has been uploaded", HttpStatus.BAD_REQUEST);
            }
            if (!isValidFileFormat(file.getOriginalFilename())) {
                return new ResponseEntity<>("Invalid file format Only JPEG, JPG, PNG files are allowed", HttpStatus.BAD_REQUEST);

            }
            BlobMetadataDTO blobMetadataDTO = mediaService.uploadMedia(file, type, userId);
            String mediaId = blobMetadataDTO.getMediaId();
            return ResponseEntity.ok(mediaId);

        }

        private boolean isValidFileFormat(String fileName) {
            String[] validFileFormats = {"jpeg", "jpg", "png"};
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            for (String validFileFormat : validFileFormats) {
                if (fileExtension.equalsIgnoreCase(validFileFormat)) {
                    return true;
                }
            }
            return false;
        }
}
