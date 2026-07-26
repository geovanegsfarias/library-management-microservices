package com.github.geovanegsfarias.infrastructure.beans;

import com.github.geovanegsfarias.core.exception.BookNotFoundException;
import com.github.geovanegsfarias.core.exception.BookUnavailableException;
import com.github.geovanegsfarias.core.exception.UnauthorizedAccessException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new BookUnavailableException("Book unavailable");
            case 404 -> new BookNotFoundException("Book not found");
            case 401, 403 -> new UnauthorizedAccessException("Unauthorized access to book-service");
            default -> new RuntimeException("Unexpected error while calling book-service");
        };
    }
}