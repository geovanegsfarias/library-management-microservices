package com.github.geovanegsfarias.infrastructure.producer;

import com.github.geovanegsfarias.infrastructure.beans.RabbitMQProperties;
import com.github.geovanegsfarias.infrastructure.dto.LoanOverdueEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties configurationProperties;

    public NotificationProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties configurationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.configurationProperties = configurationProperties;
    }

    public void sendNotification(LoanOverdueEvent event) {
        log.debug("Loan overdue notification sent to {}", event.userEmail());

        rabbitTemplate.convertAndSend(configurationProperties.exchangeName(), configurationProperties.notificationQueue(), event);
    }
}