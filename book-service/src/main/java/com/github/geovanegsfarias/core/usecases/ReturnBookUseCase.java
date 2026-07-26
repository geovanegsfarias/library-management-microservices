package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.infrastructure.exception.BookNotFoundException;

public class ReturnBookUseCase {

    private final BookGateway bookGateway;

    public ReturnBookUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public Book returnBook(Long id) {
        var bookToReturn = bookGateway.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookToReturn.setAvailableCopies(bookToReturn.getAvailableCopies() + 1);
        return bookGateway.save(bookToReturn);
    }
}
