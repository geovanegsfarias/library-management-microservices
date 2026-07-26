package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.entities.BookAvailable;
import com.github.geovanegsfarias.core.gateway.BookClientGateway;
import com.github.geovanegsfarias.infrastructure.beans.ApiKeyConfigurationProperties;
import com.github.geovanegsfarias.infrastructure.client.BookClient;
import org.springframework.stereotype.Component;

@Component
public class BookClientGatewayImpl implements BookClientGateway {

    private final BookClient bookClient;
    private final ApiKeyConfigurationProperties apiKeyConfigurationProperties;

    public BookClientGatewayImpl(BookClient bookClient, ApiKeyConfigurationProperties apiKeyConfigurationProperties) {
        this.bookClient = bookClient;
        this.apiKeyConfigurationProperties = apiKeyConfigurationProperties;
    }

    @Override
    public BookAvailable reserveBook(Long bookId) {
        var bookResponse = bookClient.reserveBook(bookId, apiKeyConfigurationProperties.apiKey());
        return new BookAvailable(bookResponse.id(), bookResponse.title());
    }

    @Override
    public void returnBook(Long bookId) {
        bookClient.returnBook(bookId, apiKeyConfigurationProperties.apiKey());
    }
}