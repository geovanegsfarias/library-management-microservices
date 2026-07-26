package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;

import java.util.List;

public class FindAllBooksUseCase {

    private final BookGateway bookGateway;

    public FindAllBooksUseCase(BookGateway bookGateway) {
        this.bookGateway = bookGateway;
    }

    public List<Book> findAllBooks() {
        return bookGateway.findAll();
    }
}
