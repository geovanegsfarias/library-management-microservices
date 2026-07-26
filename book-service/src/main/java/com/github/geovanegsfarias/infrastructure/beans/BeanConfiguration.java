package com.github.geovanegsfarias.infrastructure.beans;

import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.core.usecases.*;
import com.github.geovanegsfarias.infrastructure.gateway.BookRepositoryGateway;
import com.github.geovanegsfarias.infrastructure.mapper.BookEntityMapper;
import com.github.geovanegsfarias.infrastructure.persistence.BookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BookGateway bookGateway(BookRepository bookRepository, BookEntityMapper bookEntityMapper) {
        return new BookRepositoryGateway(bookRepository, bookEntityMapper);
    }

    @Bean
    public FindAllBooksUseCase findAllBooksUseCase(BookGateway bookGateway) {
        return new FindAllBooksUseCase(bookGateway);
    }

    @Bean
    public FindBookByIdUseCase findBookByIdUseCase(BookGateway bookGateway) {
        return new FindBookByIdUseCase(bookGateway);
    }

    @Bean
    public SaveBookUseCase saveBookUseCase(BookGateway bookGateway) {
        return new SaveBookUseCase(bookGateway);
    }

    @Bean
    public UpdateBookUseCase updateBookUseCase(BookGateway bookGateway) {
        return new UpdateBookUseCase(bookGateway);
    }

    @Bean
    public DeleteBookUseCase deleteBookUseCase(BookGateway bookGateway) {
        return new DeleteBookUseCase(bookGateway);
    }

    @Bean
    public ReserveBookUseCase reserveBookUseCase(BookGateway bookGateway) {
        return new ReserveBookUseCase(bookGateway);
    }

    @Bean
    public ReturnBookUseCase returnBookUseCase(BookGateway bookGateway) {
        return new ReturnBookUseCase(bookGateway);
    }

}