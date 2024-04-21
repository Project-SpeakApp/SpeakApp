package com.speakapp.blobservice.utils;

import com.speakapp.blobservice.exceptions.ServiceLayerException;
import com.speakapp.blobservice.exceptions.multipart_file.EmptyMultipartFileException;
import com.speakapp.blobservice.exceptions.multipart_file.MultipartFileForbiddenMimeTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultipartFileValidator {

    public static void validateMultipartFile(MultipartFile multipartFile) {

        if(multipartFile.isEmpty()) {
            throw new EmptyMultipartFileException();
        }

        String contentType = multipartFile.getContentType();

        if(contentType == null) {
            throw new ServiceLayerException(
                    "MultipartFile mime type is null",
                    HttpStatus.BAD_REQUEST
            );
        }

        String regex = "^image/.*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contentType);

        if(!matcher.matches()) {
            throw new MultipartFileForbiddenMimeTypeException();
        }
    }
}
