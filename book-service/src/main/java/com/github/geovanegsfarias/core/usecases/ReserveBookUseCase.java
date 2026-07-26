package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;

public class ReserveBookUseCase {

    private final BookGateway bookGateway;
    private final FindBookByIdUseCase findBookByIdUseCase;

    public ReserveBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        this.bookGateway = bookGateway;
        this.findBookByIdUseCase = findBookByIdUseCase;
    }

    public Book reserveBook(Long id) {
        var bookToReserve = findBookByIdUseCase.findBookById(id);
        bookToReserve.reserveCopy();
        return bookGateway.save(bookToReserve);
    }
}