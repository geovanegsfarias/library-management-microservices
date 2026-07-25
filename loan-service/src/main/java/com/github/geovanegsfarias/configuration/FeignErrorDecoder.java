package com.github.geovanegsfarias.configuration;

import com.github.geovanegsfarias.exception.BookNotFoundException;
import com.github.geovanegsfarias.exception.BookUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 404 -> new BookNotFoundException("Book not found");
            case 409 -> new BookUnavailableException("Book unavailable");
            case 401 -> new IllegalArgumentException("Unauthorized access");
            default -> new RuntimeException("Unexpected error while calling book-service");
        };
    }
}