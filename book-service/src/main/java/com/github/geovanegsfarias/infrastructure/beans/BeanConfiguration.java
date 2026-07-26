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
    public UpdateBookUseCase updateBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        return new UpdateBookUseCase(bookGateway, findBookByIdUseCase);
    }

    @Bean
    public DeleteBookUseCase deleteBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        return new DeleteBookUseCase(bookGateway, findBookByIdUseCase);
    }

    @Bean
    public ReserveBookUseCase reserveBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        return new ReserveBookUseCase(bookGateway, findBookByIdUseCase);
    }

    @Bean
    public ReturnBookUseCase returnBookUseCase(BookGateway bookGateway, FindBookByIdUseCase findBookByIdUseCase) {
        return new ReturnBookUseCase(bookGateway, findBookByIdUseCase);
    }

}