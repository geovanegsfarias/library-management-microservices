package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.infrastructure.exception.BookNotFoundException;

public class UpdateBookUseCase {

    private final BookGateway bookGateway;

    public UpdateBookUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public void updateBook(Book bookToUpdate) {
        assertAvailableCopiesIsValid(bookToUpdate);
        var savedBook = bookGateway.findById(bookToUpdate.getId()).orElseThrow(() -> new BookNotFoundException("Book not found"));
        savedBook.setTitle(bookToUpdate.getTitle());
        savedBook.setAuthor(bookToUpdate.getAuthor());
        savedBook.setPublisher(bookToUpdate.getPublisher());
        savedBook.setTotalCopies(bookToUpdate.getTotalCopies());
        savedBook.setAvailableCopies(bookToUpdate.getAvailableCopies());
        bookGateway.save(savedBook);
    }

    private void assertAvailableCopiesIsValid(Book book) {
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot be greater than total copies");
        }
    }
}
