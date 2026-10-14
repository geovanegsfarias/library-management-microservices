package com.github.geovanegsfarias.consumer;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.mapper.NotificationMapper;
import com.github.geovanegsfarias.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "broker.type", havingValue = "rabbitmq", matchIfMissing = true)
@Slf4j
public class RabbitNotificationConsumer {
    private final NotificationMapper mapper;
    private final NotificationService notificationService;

    public RabbitNotificationConsumer(NotificationMapper mapper, NotificationService notificationService) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${broker.rabbitmq.notification-queue}")
    public void listener(LoanOverdueEvent overdueEvent) {
        log.debug("Received loan overdue event for loan id {}", overdueEvent.loanId());

        var notification = mapper.toNotification(overdueEvent);

        notificationService.sendNotification(notification, overdueEvent);
    }
}