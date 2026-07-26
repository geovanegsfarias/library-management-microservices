package com.github.geovanegsfarias.core.gateway;

import com.github.geovanegsfarias.core.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookGateway {

    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void delete(Book book);
}