package com.github.geovanegsfarias.core.exception;

public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException(String message) {
        super(message);
    }
}
