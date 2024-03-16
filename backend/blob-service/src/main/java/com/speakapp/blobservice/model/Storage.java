package com.speakapp.blobservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    private String path;
    private String fileName;
    private InputStream inputStream;
}
