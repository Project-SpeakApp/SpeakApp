package com.speakapp.blobservice.exceptions.multipart_file;

import com.speakapp.blobservice.exceptions.ServiceLayerException;
import org.springframework.http.HttpStatus;

public class EmptyMultipartFileException extends ServiceLayerException {
    private static final String DEFAULT_MESSAGE = "Bad request: the file cannot be empty.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    public EmptyMultipartFileException() {
        super(DEFAULT_MESSAGE, HTTP_STATUS);
    }
}
