package com.github.geovanegsfarias.infrastructure.producer;

import com.github.geovanegsfarias.infrastructure.beans.SnsProperties;
import com.github.geovanegsfarias.infrastructure.dto.LoanOverdueEvent;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "broker.type", havingValue = "aws")
@Slf4j
public class SnsNotificationProducer implements NotificationProducer {
    private final SnsTemplate snsTemplate;
    private final SnsProperties snsProperties;

    public SnsNotificationProducer(SnsTemplate snsTemplate, SnsProperties snsProperties) {
        this.snsTemplate = snsTemplate;
        this.snsProperties = snsProperties;
    }

    @Override
    public void sendNotification(LoanOverdueEvent overdueEvent) {
        log.debug("Loan overdue notification sent to {}", overdueEvent.userEmail());

        snsTemplate.convertAndSend(snsProperties.arn(), overdueEvent);
    }
}