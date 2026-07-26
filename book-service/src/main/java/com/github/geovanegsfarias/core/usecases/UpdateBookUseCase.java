package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;

public class UpdateBookUseCase {

    private final BookGateway bookGateway;
    private final FindBookByIdUseCase findBookByIdUseCase;

    public UpdateBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        this.bookGateway = bookGateway;
        this.findBookByIdUseCase = findBookByIdUseCase;
    }

    public void updateBook(Book bookToUpdate) {
        assertAvailableCopiesIsValid(bookToUpdate);

        var savedBook = findBookByIdUseCase.findBookById(bookToUpdate.getId());

        savedBook.updateBook(
                bookToUpdate.getTitle(),
                bookToUpdate.getAuthor(),
                bookToUpdate.getPublisher(),
                bookToUpdate.getTotalCopies(),
                bookToUpdate.getAvailableCopies()
        );

        bookGateway.save(savedBook);
    }

    private void assertAvailableCopiesIsValid(Book book) {
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot be greater than total copies");
        }
    }
}
