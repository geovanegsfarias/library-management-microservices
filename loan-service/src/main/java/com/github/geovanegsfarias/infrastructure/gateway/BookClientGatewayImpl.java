package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.entities.BookAvailable;
import com.github.geovanegsfarias.core.gateway.BookClientGateway;
import com.github.geovanegsfarias.infrastructure.beans.ApiKeyProperties;
import com.github.geovanegsfarias.infrastructure.client.BookClient;
import org.springframework.stereotype.Component;

@Component
public class BookClientGatewayImpl implements BookClientGateway {

    private final BookClient bookClient;
    private final ApiKeyProperties apiKeyProperties;

    public BookClientGatewayImpl(BookClient bookClient, ApiKeyProperties apiKeyProperties) {
        this.bookClient = bookClient;
        this.apiKeyProperties = apiKeyProperties;
    }

    @Override
    public BookAvailable reserveBook(Long bookId) {
        var bookResponse = bookClient.reserveBook(bookId, apiKeyProperties.apiKey());
        return new BookAvailable(bookResponse.id(), bookResponse.title());
    }

    @Override
    public void returnBook(Long bookId) {
        bookClient.returnBook(bookId, apiKeyProperties.apiKey());
    }
}