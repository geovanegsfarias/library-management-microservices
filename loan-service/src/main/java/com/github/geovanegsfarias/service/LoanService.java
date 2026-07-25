package com.github.geovanegsfarias.service;

import com.github.geovanegsfarias.client.BookClient;
import com.github.geovanegsfarias.configuration.ApiKeyConfigurationProperties;
import com.github.geovanegsfarias.exception.LoanAlreadyReturnedException;
import com.github.geovanegsfarias.exception.LoanNotFoundException;
import com.github.geovanegsfarias.mapper.LoanMapper;
import com.github.geovanegsfarias.model.Loan;
import com.github.geovanegsfarias.model.LoanStatus;
import com.github.geovanegsfarias.producer.NotificationProducer;
import com.github.geovanegsfarias.repository.LoanRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper mapper;
    private final BookClient bookClient;
    private final NotificationProducer notificationProducer;
    private final UserService userService;
    private final ApiKeyConfigurationProperties configurationProperties;

    public LoanService(LoanRepository loanRepository, LoanMapper mapper, BookClient bookClient, NotificationProducer notificationProducer, UserService userService, ApiKeyConfigurationProperties configurationProperties) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
        this.bookClient = bookClient;
        this.notificationProducer = notificationProducer;
        this.userService = userService;
        this.configurationProperties = configurationProperties;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findByIdOrThrowException(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new LoanNotFoundException("Loan not found"));
    }

    public Loan save(Loan loanToSave, String userEmail) {
        var bookId = loanToSave.getBookId();

        bookClient.reserveBook(bookId, configurationProperties.apiKey());

        var authenticatedUser = userService.findByEmailOrThrowException(userEmail);
        loanToSave.setUser(authenticatedUser);

        loanToSave.setStatus(LoanStatus.ACTIVE);

        loanToSave.setDueDate(Instant.now().plus(30, ChronoUnit.DAYS));

        return loanRepository.save(loanToSave);
    }

    public Loan returnLoan(Long id) {
        var loanToReturn = findByIdOrThrowException(id);

        assertLoanIsNotReturned(loanToReturn);

        bookClient.returnBook(loanToReturn.getBookId(), configurationProperties.apiKey());

        loanToReturn.setStatus(LoanStatus.RETURNED);

        loanToReturn.setReturnedDate(Instant.now());

        return loanRepository.save(loanToReturn);
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void processOverdueLoans() {
        var loanList = loanRepository.findByStatusAndDueDateBefore(LoanStatus.ACTIVE, Instant.now());

        loanList.forEach(loan -> {
            loan.setStatus(LoanStatus.OVERDUE);
            loanRepository.save(loan);
            notificationProducer.sendNotification(mapper.toLoanOverdueEvent(loan));
        });
    }

    private void assertLoanIsNotReturned(Loan loan) {
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturnedException("Loan already returned");
        }
    }

}