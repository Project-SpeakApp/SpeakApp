package com.speakapp.blobservice.service;

import com.speakapp.blobservice.exception.AzureBlobStorageException;
import com.speakapp.blobservice.model.Storage;

import java.util.List;

public interface IAzureBlobStorage {
    public String write(Storage storage) throws AzureBlobStorageException;

    public String update(Storage storage) throws AzureBlobStorageException;

    public byte[] read(Storage storage) throws AzureBlobStorageException;

    public List<String> listFiles(Storage storage) throws AzureBlobStorageException;

    public void delete(Storage storage) throws AzureBlobStorageException;

    public void createContainer() throws AzureBlobStorageException;

    public void deleteContainer() throws AzureBlobStorageException;
}
