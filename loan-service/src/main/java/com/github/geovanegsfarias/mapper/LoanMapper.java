package com.github.geovanegsfarias.mapper;

import com.github.geovanegsfarias.dto.CreateLoanRequest;
import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.dto.LoanResponse;
import com.github.geovanegsfarias.model.Loan;
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

    LoanOverdueEvent toLoanOverdueEvent(Loan loan);
}