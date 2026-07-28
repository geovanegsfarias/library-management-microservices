package com.github.geovanegsfarias.infrastructure.mapper;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.infrastructure.dto.CreateLoanRequest;
import com.github.geovanegsfarias.infrastructure.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.infrastructure.dto.LoanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoanMapper {

    Loan toLoan(CreateLoanRequest request);

    @Mapping(target = "userEmail", source = "user.email")
    LoanResponse toLoanResponse(Loan loan);

    List<LoanResponse> toLoanResponseList(List<Loan> loans);

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "userEmail", source = "user.email")
    LoanOverdueEvent toLoanOverdueEvent(Loan loan);
}