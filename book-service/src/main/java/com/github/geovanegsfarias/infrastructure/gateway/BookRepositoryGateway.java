package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.core.gateway.BookGateway;
import com.github.geovanegsfarias.infrastructure.mapper.BookEntityMapper;
import com.github.geovanegsfarias.infrastructure.persistence.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookRepositoryGateway implements BookGateway {

    private final BookRepository bookRepository;
    private final BookEntityMapper entityMapper;

    public BookRepositoryGateway(BookRepository bookRepository, BookEntityMapper entityMapper) {
        this.bookRepository = bookRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll().stream().map(entityMapper::toDomain).toList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public Book save(Book book) {
        var entity = entityMapper.toEntity(book);
        var savedBookEntity = bookRepository.save(entity);
        return entityMapper.toDomain(savedBookEntity);
    }

    @Override
    public void delete(Book book) {
        bookRepository.deleteById(book.getId());
    }
}
