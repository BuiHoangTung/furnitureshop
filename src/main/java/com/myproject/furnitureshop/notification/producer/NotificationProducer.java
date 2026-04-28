package com.myproject.furnitureshop.notification.producer;

import com.myproject.furnitureshop.notification.model.NotificationMessage;
import com.myproject.furnitureshop.notification.config.NotificationMQConfigProperties;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    private final NotificationMQConfigProperties properties;

    public NotificationProducer(RabbitTemplate rabbitTemplate, NotificationMQConfigProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void pubNotification(NotificationMessage notificationMessage) {
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString());

            return message;
        };

        this.rabbitTemplate.convertAndSend(
                this.properties.exchange(),
                notificationMessage.getChannel().getMqRouteKey(),
                notificationMessage,
                messagePostProcessor
        );
    }
}
