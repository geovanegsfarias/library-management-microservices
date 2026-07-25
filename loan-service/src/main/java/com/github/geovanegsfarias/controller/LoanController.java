package com.github.geovanegsfarias.controller;

import com.github.geovanegsfarias.dto.CreateLoanRequest;
import com.github.geovanegsfarias.dto.LoanResponse;
import com.github.geovanegsfarias.mapper.LoanMapper;
import com.github.geovanegsfarias.service.LoanService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/loans")
@Slf4j
public class LoanController {
    private final LoanService loanService;
    private final LoanMapper mapper;

    public LoanController(LoanService loanService, LoanMapper mapper) {
        this.loanService = loanService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        log.debug("Request received to list all loans");

        var loans = loanService.findAll();

        var loanResponseList = mapper.toLoanResponseList(loans);

        return ResponseEntity.ok(loanResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long id) {
        log.debug("Request received to find loan by id {}", id);

        var loan = loanService.findByIdOrThrowException(id);

        var loanResponse = mapper.toLoanResponse(loan);

        return ResponseEntity.ok(loanResponse);
    }

    @PostMapping
    public ResponseEntity<LoanResponse> saveLoan(@RequestBody @Valid CreateLoanRequest request, Authentication authentication) {
        log.debug("Request received to save loan {}", request);

        var loanToSave = mapper.toLoan(request);

        var savedLoan = loanService.save(loanToSave, authentication.getName());

        var loanResponse = mapper.toLoanResponse(savedLoan);

        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {
        log.debug("Request received to release loan by id {}", id);

        var returnedLoan = loanService.returnLoan(id);

        var loanResponse = mapper.toLoanResponse(returnedLoan);

        return ResponseEntity.ok(loanResponse);
    }

}