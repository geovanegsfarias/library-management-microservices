package com.github.geovanegsfarias.infrastructure.producer;

import com.github.geovanegsfarias.infrastructure.beans.RabbitMQProperties;
import com.github.geovanegsfarias.infrastructure.dto.LoanOverdueEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "broker.type", havingValue = "rabbitmq", matchIfMissing = true)
@Slf4j
public class RabbitNotificationProducer implements NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties configurationProperties;

    public RabbitNotificationProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties configurationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.configurationProperties = configurationProperties;
    }

    public void sendNotification(LoanOverdueEvent overdueEvent) {
        log.debug("Loan overdue notification sent to {}", overdueEvent.userEmail());

        rabbitTemplate.convertAndSend(configurationProperties.exchangeName(), configurationProperties.notificationQueue(), overdueEvent);
    }
}