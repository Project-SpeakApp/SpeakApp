package com.speakapp.userservice.controllers;

import com.speakapp.userservice.dtos.AppUserCreateDTO;
import com.speakapp.userservice.dtos.MessageRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaUserController {

    private static final String TEST_TOPIC = "test-topic";
    private static final String TEST_GROUP = "test-group";

    private final KafkaTemplate<String, AppUserCreateDTO> kafkaTemplate;

    public KafkaUserController(@Qualifier("kafkaTemplate") KafkaTemplate<String, AppUserCreateDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send-message")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMessage(@RequestBody AppUserCreateDTO appUserCreateDTO) {
        System.out.println(appUserCreateDTO.toString());
        kafkaTemplate.send(TEST_TOPIC, appUserCreateDTO);
    }

    @KafkaListener(topics = TEST_TOPIC, groupId = TEST_GROUP)
    public void listener1(String message) {
        System.out.println("Message: '" + message + "' from listener1");
    }
}
