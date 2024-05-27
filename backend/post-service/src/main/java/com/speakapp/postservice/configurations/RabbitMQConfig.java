package com.speakapp.postservice.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    public static final String FILE_DELETION_QUEUE = "fileDeletionQueue";
    public static final String DELAYED_EXCHANGE = "delayedExchange";

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue fileDeletionQueue() {
        return new Queue(FILE_DELETION_QUEUE, true);
    }

    @Bean
    public Binding binding(Queue fileDeletionQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(fileDeletionQueue).to(delayedExchange).with(FILE_DELETION_QUEUE).noargs();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(DELAYED_EXCHANGE);
        template.setRoutingKey(FILE_DELETION_QUEUE);
        return template;
    }
}
