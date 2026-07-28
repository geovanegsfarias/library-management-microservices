package com.github.geovanegsfarias.infrastructure.presentation;

import com.github.geovanegsfarias.core.usecases.FindAllLoansUseCase;
import com.github.geovanegsfarias.core.usecases.FindLoanByIdUseCase;
import com.github.geovanegsfarias.core.usecases.ReturnLoanUseCase;
import com.github.geovanegsfarias.core.usecases.SaveLoanUseCase;
import com.github.geovanegsfarias.infrastructure.dto.CreateLoanRequest;
import com.github.geovanegsfarias.infrastructure.dto.LoanResponse;
import com.github.geovanegsfarias.infrastructure.mapper.LoanMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/loans")
@Slf4j
@Tag(name = "Loan")
public class LoanController {
    private final FindAllLoansUseCase findAllLoansUseCase;
    private final FindLoanByIdUseCase findLoanByIdUseCase;
    private final ReturnLoanUseCase returnLoanUseCase;
    private final SaveLoanUseCase saveLoanUseCase;
    private final LoanMapper mapper;

    public LoanController(FindAllLoansUseCase findAllLoansUseCase, FindLoanByIdUseCase findLoanByIdUseCase, ReturnLoanUseCase returnLoanUseCase, SaveLoanUseCase saveLoanUseCase, LoanMapper mapper) {
        this.findAllLoansUseCase = findAllLoansUseCase;
        this.findLoanByIdUseCase = findLoanByIdUseCase;
        this.returnLoanUseCase = returnLoanUseCase;
        this.saveLoanUseCase = saveLoanUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List loans")
    @ApiResponse(responseCode = "200", description = "Loans returned")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        log.debug("Request received to list all loans");

        var loans = findAllLoansUseCase.findAll();

        var loanResponseList = mapper.toLoanResponseList(loans);

        return ResponseEntity.ok(loanResponseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan")
    @ApiResponse(responseCode = "200", description = "Loan returned")
    @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long id) {
        log.debug("Request received to find loan by id {}", id);

        var loan = findLoanByIdUseCase.findById(id);

        var loanResponse = mapper.toLoanResponse(loan);

        return ResponseEntity.ok(loanResponse);
    }

    @PostMapping
    @Operation(summary = "Create loan")
    @ApiResponse(responseCode = "201", description = "Loan created")
    @ApiResponse(responseCode = "400", description = "Book unavailable")
    @ApiResponse(responseCode = "403", description = "Unauthorized access", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<LoanResponse> saveLoan(@RequestBody @Valid CreateLoanRequest request, Authentication authentication) {
        log.debug("Request received to save loan {}", request);

        var loanToSave = mapper.toLoan(request);

        var savedLoan = saveLoanUseCase.save(loanToSave, authentication.getName());

        var loanResponse = mapper.toLoanResponse(savedLoan);

        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return loan")
    @ApiResponse(responseCode = "200", description = "Loan returned")
    @ApiResponse(responseCode = "403", description = "Unauthorized access", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Loan or book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {
        log.debug("Request received to release loan by id {}", id);

        var returnedLoan = returnLoanUseCase.returnLoan(id);

        var loanResponse = mapper.toLoanResponse(returnedLoan);

        return ResponseEntity.ok(loanResponse);
    }

}