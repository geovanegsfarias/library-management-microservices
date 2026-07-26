package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;

public class SaveBookUseCase {

    private final BookGateway bookGateway;

    public SaveBookUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public Book saveBook(Book bookToSave) {
        assertAvailableCopiesIsValid(bookToSave);
        return bookGateway.save(bookToSave);
    }

    private void assertAvailableCopiesIsValid(Book book) {
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot be greater than total copies");
        }
    }
}
