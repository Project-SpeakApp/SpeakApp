package com.speakapp.blobservice;

import com.speakapp.blobservice.exception.AzureBlobStorageException;
import com.speakapp.blobservice.model.Storage;
import com.speakapp.blobservice.service.impl.AzureBlobStorageImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AzureBlobStorageApplicationTests {

    @Autowired
    AzureBlobStorageImpl storageImpl;

    @BeforeEach
    public void createContainer() throws AzureBlobStorageException {
        storageImpl.createContainer();
    }

    @AfterEach
    public void deleteContainer() throws AzureBlobStorageException {
        storageImpl.deleteContainer();
    }

    @Test
    void writeBlob() throws AzureBlobStorageException {
        //given
        Storage storage = getStorage("customStorage", "customFile.txt", "Hello World");

        //when
        storageImpl.write(storage);

        //then
        String expected_data = "Hello World";
        String actual_data = new String(storageImpl.read(storage));

        assertEquals(expected_data, actual_data);
    }

    @Test
    void updateBlob() throws AzureBlobStorageException {
        //given
        Storage storage = getStorage("customStorage", "customFile.txt", "Hello World");
        storageImpl.write(storage);

        //when
        storage = getStorage("customStorage", "customFile.txt", "Hello World1");
        storageImpl.update(storage);

        //then
        String expected_data = "Hello World1";
        String actual_data = new String(storageImpl.read(storage));

        assertEquals(expected_data, actual_data);
    }

    @Test
    void listFiles() throws AzureBlobStorageException {
        //given
        Storage storage = getStorage("customStorage", "customFile.txt", "Hello World");

        storageImpl.write(storage);

        //when
        List<String> listFiles = storageImpl.listFiles(storage);

        //then
        assertEquals(1, listFiles.size());
    }

    @Test
    void deleteBlob() throws AzureBlobStorageException {
        //given
        Storage storage = getStorage("customStorage", "customFile.txt", "Hello World");
        storageImpl.write(storage);

        //when
        storageImpl.delete(storage);

        //then
        List<String> listFiles = storageImpl.listFiles(storage);
        assertEquals(0, listFiles.size());
    }

    private Storage getStorage(String path ,String fileName,String data){
        Storage storage = new Storage();
        storage.setPath(path);
        storage.setFileName(fileName);
        if(StringUtils.isNotBlank(data)){
            storage.setInputStream(new ByteArrayInputStream(data.getBytes()));
        }

        return storage;
    }
}
