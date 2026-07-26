package com.github.geovanegsfarias.core.usecases;


import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.infrastructure.exception.BookNotFoundException;

public class DeleteBookUseCase {

    private final BookGateway bookGateway;

    public DeleteBookUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public void deleteBook(Long id) {
        var bookToDelete = bookGateway.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookGateway.delete(bookToDelete);
    }
}