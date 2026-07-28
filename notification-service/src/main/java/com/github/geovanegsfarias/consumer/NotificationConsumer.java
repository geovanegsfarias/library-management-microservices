package com.github.geovanegsfarias.consumer;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.mapper.NotificationMapper;
import com.github.geovanegsfarias.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {
    private final NotificationMapper mapper;
    private final NotificationService notificationService;

    public NotificationConsumer(NotificationMapper mapper, NotificationService notificationService) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${broker.notification-queue}")
    public void listener(LoanOverdueEvent event) {
        log.debug("Received loan overdue event for loan id {}", event.loanId());

        var notification = mapper.toNotification(event);

        notificationService.sendNotification(notification, event);
    }
}