package com.myproject.furnitureshop.notification.consumer;

import com.myproject.furnitureshop.notification.model.NotificationMessage;
import com.myproject.furnitureshop.notification.service.EmailService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationConsumer {
    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "notification.email.queue")
    public void handleEmailNotification(@Payload NotificationMessage payload,
                                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                        Channel channel) throws IOException {
        try {
            // Logic to send
            this.emailService.sendSimpleEmail(payload);

            channel.basicAck(deliveryTag, false);
        } catch(Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
