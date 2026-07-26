package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.gateway.NotificationGateway;
import com.github.geovanegsfarias.infrastructure.mapper.LoanMapper;
import com.github.geovanegsfarias.infrastructure.producer.NotificationProducer;
import org.springframework.stereotype.Component;

@Component
public class NotificationGatewayImpl implements NotificationGateway {

    private final NotificationProducer notificationProducer;
    private final LoanMapper loanMapper;

    public NotificationGatewayImpl(NotificationProducer notificationProducer, LoanMapper loanMapper) {
        this.notificationProducer = notificationProducer;
        this.loanMapper = loanMapper;
    }

    @Override
    public void sendOverdueNotification(Loan loan) {
        notificationProducer.sendNotification(loanMapper.toLoanOverdueEvent(loan));
    }
}