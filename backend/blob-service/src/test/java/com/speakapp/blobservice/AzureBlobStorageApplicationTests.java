package com.speakapp.blobservice;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AzureBlobStorageApplicationTests {
    @Test
    void contextLoads() {
    }
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void whenFileUploaded_thenVerifyStatus()
            throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/api/media/upload")
                        .file(file)
                        .param("type", "IMAGE")
                        .header("UserId", "6c84fb95-12c4-11ec-82a8-0242ac130003"))
                .andExpect(status().isOk());
    }

    @Test
    void whenEmptyFileUploaded_thenVerifyStatusNotFound() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/api/media/upload")
                        .file(file)
                        .param("type", "AVATAR")  // Add type parameter
                        .header("UserId", "6c84fb95-12c4-11ec-82a8-0242ac130003")) // Add UserId header
                .andExpect(status().isNotFound());
    }


}
