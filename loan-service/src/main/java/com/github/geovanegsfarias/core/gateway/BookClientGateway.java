package com.github.geovanegsfarias.core.gateway;

import com.github.geovanegsfarias.core.entities.BookAvailable;

public interface BookClientGateway {

    BookAvailable reserveBook(Long bookId);

    void returnBook(Long bookId);
}
