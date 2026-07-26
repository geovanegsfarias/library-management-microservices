package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.infrastructure.exception.BookNotFoundException;

public class FindBookByIdUseCase {

    private final BookGateway bookGateway;

    public FindBookByIdUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public Book findBookById(Long id) {
        return bookGateway.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }
}
