package com.github.geovanegsfarias.core.entities;

import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.exception.LoanAlreadyReturnedException;

import java.time.Instant;

public class Loan {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private User user;
    private Instant loanDate;
    private Instant dueDate;
    private Instant returnedDate;
    private LoanStatus status;

    public Loan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Instant loanDate) {
        this.loanDate = loanDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Instant returnedDate) {
        this.returnedDate = returnedDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public void initLoan(String bookTitle, User user) {
        this.bookTitle = bookTitle;
        this.user = user;
        this.status = LoanStatus.ACTIVE;
        this.dueDate = java.time.Instant.now().plus(30, java.time.temporal.ChronoUnit.DAYS);
    }

    public void markAsOverdue() {
        this.status = LoanStatus.OVERDUE;
    }

    public void returnLoan() {
        if (this.status == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturnedException("Loan already returned");
        }

        this.status = LoanStatus.RETURNED;
        this.returnedDate = Instant.now();
    }
}