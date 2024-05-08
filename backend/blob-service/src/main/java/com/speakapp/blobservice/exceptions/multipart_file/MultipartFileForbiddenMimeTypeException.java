package com.speakapp.blobservice.exceptions.multipart_file;

import com.speakapp.blobservice.exceptions.ServiceLayerException;
import org.springframework.http.HttpStatus;

public class MultipartFileForbiddenMimeTypeException extends ServiceLayerException {
    private static final String DEFAULT_MESSAGE = "Forbidden: this file mime type is not allowed.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;
    public MultipartFileForbiddenMimeTypeException() {
        super(DEFAULT_MESSAGE, HTTP_STATUS);
    }
}
