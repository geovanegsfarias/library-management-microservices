package com.github.geovanegsfarias.producer;

import com.github.geovanegsfarias.configuration.RabbitMQConfigurationProperties;
import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfigurationProperties configurationProperties;

    public NotificationProducer(RabbitTemplate rabbitTemplate, RabbitMQConfigurationProperties configurationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.configurationProperties = configurationProperties;
    }

    public void sendNotification(LoanOverdueEvent event) {
        log.debug("Loan overdue notification sent to {}", event.userEmail());

        rabbitTemplate.convertAndSend(configurationProperties.exchangeName(), configurationProperties.notificationQueue(), event);
    }
}