package com.github.geovanegsfarias.consumer;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.mapper.NotificationMapper;
import com.github.geovanegsfarias.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private final NotificationMapper mapper;
    private final NotificationService notificationService;

    public NotificationConsumer(NotificationMapper mapper, NotificationService notificationService) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${broker.notification-queue}")
    public void listener(LoanOverdueEvent event) {
        var notification = mapper.toNotification(event);

        notificationService.sendNotification(notification);
    }
}