package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.gateway.BookGateway;

public class DeleteBookUseCase {

    private final BookGateway bookGateway;
    private final FindBookByIdUseCase findBookByIdUseCase;

    public DeleteBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        this.bookGateway = bookGateway;
        this.findBookByIdUseCase = findBookByIdUseCase;
    }

    public void deleteBook(Long id) {
        var bookToDelete = findBookByIdUseCase.findBookById(id);
        bookGateway.delete(bookToDelete);
    }
}