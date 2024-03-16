package com.speakapp.blobservice.service.impl;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobStorageException;
import com.speakapp.blobservice.exception.AzureBlobStorageException;
import com.speakapp.blobservice.model.Storage;
import com.speakapp.blobservice.service.IAzureBlobStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AzureBlobStorageImpl implements IAzureBlobStorage {
    @Value("${azure.storage.container.name}")
    private String containerName;

    @Autowired
    BlobServiceClient blobServiceClient;

    @Autowired
    BlobContainerClient blobContainerClient;


    @Override
    public String write(Storage storage) throws AzureBlobStorageException {
        try {
            BlobClient blob = blobContainerClient.getBlobClient(getPath(storage));
            blob.upload(storage.getInputStream(), false);
            return getPath(storage);
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    @Override
    public String update(Storage storage) throws AzureBlobStorageException{
        try {
            BlobClient blob = blobContainerClient.getBlobClient(getPath(storage));
            blob.upload(storage.getInputStream(), true);
            return getPath(storage);
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    @Override
    public byte[] read(Storage storage) throws AzureBlobStorageException {
        try {
            BlobClient blob = blobContainerClient.getBlobClient(getPath(storage));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.downloadStream(outputStream);
            return outputStream.toByteArray();
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    @Override
    public List<String> listFiles(Storage storage) throws AzureBlobStorageException{
        try {
            PagedIterable<BlobItem> blobList = blobContainerClient.listBlobsByHierarchy(storage.getPath() + "/");
            List<String> blobNamesList = new ArrayList<>();
            for (BlobItem blobItem : blobList) {
                blobNamesList.add(blobItem.getName());
            }
            return blobNamesList;
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    @Override
    public void delete(Storage storage) throws AzureBlobStorageException{
        try {
            blobContainerClient.getBlobClient(getPath(storage)).delete();
            log.info("Blob is deleted successfully");
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    @Override
    public void createContainer() throws AzureBlobStorageException{
        try {
            blobServiceClient.createBlobContainer(containerName);
            log.info("Container created successfully");
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    @Override
    public void deleteContainer() throws AzureBlobStorageException{
        try {
            blobServiceClient.deleteBlobContainer(containerName);
            log.info("Container deleted successfully");
        } catch (BlobStorageException e) {
            throw new AzureBlobStorageException(e.getServiceMessage());
        } catch (Exception e) {
            throw new AzureBlobStorageException(e.getMessage());
        }
    }

    private String getPath(Storage storage) {
        if(StringUtils.isNotBlank(storage.getPath()) && StringUtils.isNotBlank(storage.getFileName())) {
            return storage.getPath() + "/" + storage.getFileName();
        }
        return null;

    }
}
