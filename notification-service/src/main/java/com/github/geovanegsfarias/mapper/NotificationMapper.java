package com.github.geovanegsfarias.mapper;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    Notification toNotification(LoanOverdueEvent event);
}
