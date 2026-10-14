package com.github.geovanegsfarias.infrastructure.producer;

import com.github.geovanegsfarias.infrastructure.dto.LoanOverdueEvent;

public interface NotificationProducer {

    void sendNotification(LoanOverdueEvent overdueEvent);
}