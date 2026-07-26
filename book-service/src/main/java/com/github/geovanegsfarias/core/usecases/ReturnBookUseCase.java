package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;

public class ReturnBookUseCase {

    private final BookGateway bookGateway;
    private final FindBookByIdUseCase findBookByIdUseCase;

    public ReturnBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        this.bookGateway = bookGateway;
        this.findBookByIdUseCase = findBookByIdUseCase;
    }

    public Book returnBook(Long id) {
        var bookToReturn = findBookByIdUseCase.findBookById(id);
        bookToReturn.returnCopy();
        return bookGateway.save(bookToReturn);
    }
}