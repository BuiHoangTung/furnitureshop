package com.myproject.furnitureshop.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private final NotificationMQConfigProperties notificationMQConfigProperties;

    public RabbitMQConfig(NotificationMQConfigProperties notificationMQConfigProperties) {
        this.notificationMQConfigProperties = notificationMQConfigProperties;
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder
                .durable(this.notificationMQConfigProperties.emailQueue())
                .build();
    }

    @Bean
    public Queue inappQueue() {
        return QueueBuilder
                .durable(this.notificationMQConfigProperties.inappQueue())
                .build();
    }

    @Bean
    public TopicExchange notificationExchange() {
        return ExchangeBuilder
                .topicExchange(this.notificationMQConfigProperties.exchange())
                .durable(true)
                .build();
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange notificationExchange) {
        return BindingBuilder
                .bind(emailQueue)
                .to(notificationExchange)
                .with(this.notificationMQConfigProperties.emailRoutingKey());
    }

    @Bean
    public Binding inappBinding(Queue inappQueue, TopicExchange notificationExchange) {
        return BindingBuilder
                .bind(inappQueue)
                .to(notificationExchange)
                .with(this.notificationMQConfigProperties.inappRoutingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate notificationRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setMessageConverter(jsonMessageConverter());
        template.setMandatory(true);

        template.setConfirmCallback(((correlationData, ack, cause) -> {
            if(!ack) {
                System.err.println("Message not confirmed: " + cause);
            }
        }));

        template.setReturnsCallback(returned -> {
            System.err.println("Message returned: " + returned.getMessage());
        });

        return template;
    }
}










