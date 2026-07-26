package com.github.geovanegsfarias.infrastructure.mapper;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.infrastructure.persistence.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserEntityMapper.class})
public interface LoanEntityMapper {

    Loan toDomain(LoanEntity entity);

    LoanEntity toEntity(Loan loan);
}