package com.github.geovanegsfarias.mapper;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "to", source = "userEmail")
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "body", ignore = true)
    @Mapping(target = "sendAt", ignore = true)
    Notification toNotification(LoanOverdueEvent event);
}
