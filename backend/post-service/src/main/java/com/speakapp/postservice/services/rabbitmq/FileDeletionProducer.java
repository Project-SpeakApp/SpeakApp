package com.speakapp.postservice.services.rabbitmq;

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
    public void sendFileDeletionMessage(UUID mediaId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDelay(1000); // TODO Set delay to 1 hour later
        String messageBody = "IMAGE" + "," + mediaId.toString();
        Message message = new Message(messageBody.getBytes(), messageProperties);
        rabbitTemplate.send("delayedExchange", "fileDeletionQueue", message);
    }
}
