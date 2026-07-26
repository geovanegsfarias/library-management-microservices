package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.infrastructure.exception.BookNotFoundException;
import com.github.geovanegsfarias.infrastructure.exception.BookUnavailableException;

public class ReserveBookUseCase {

    private final BookGateway bookGateway;

    public ReserveBookUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public Book reserveBook(Long id) {
        var bookToReserve = bookGateway.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        assertBookIsAvailable(bookToReserve);
        bookToReserve.setAvailableCopies(bookToReserve.getAvailableCopies() - 1);
        return bookGateway.save(bookToReserve);
    }

    private void assertBookIsAvailable(Book book) {
        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("Book unavailable");
        }
    }
}