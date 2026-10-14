package com.github.geovanegsfarias.consumer;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.mapper.NotificationMapper;
import com.github.geovanegsfarias.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SnsNotificationMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "broker.type", havingValue = "aws")
@Slf4j
public class SqsNotificationConsumer {
    private final NotificationMapper mapper;
    private final NotificationService notificationService;

    public SqsNotificationConsumer(NotificationMapper mapper, NotificationService notificationService) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @SqsListener("${broker.sqs.queue-url}")
    public void listener(@SnsNotificationMessage LoanOverdueEvent overdueEvent) {
        log.debug("Received loan overdue event for loan id {}", overdueEvent.loanId());

        var notification = mapper.toNotification(overdueEvent);

        notificationService.sendNotification(notification, overdueEvent);
    }

}
