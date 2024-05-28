package com.speakapp.userservice.services.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileDeletionProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendFileDeletionMessage(String type, UUID mediaId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(60 * 5 * 1000); // 5 minutes
        String messageBody = type + "," + mediaId;
        Message message = new Message(messageBody.getBytes(), messageProperties);
        rabbitTemplate.send("delayedExchange", "fileDeletionQueue", message);
    }
}
