package com.github.geovanegsfarias.service;

import com.github.geovanegsfarias.configuration.ApiKeyConfigurationProperties;
import com.github.geovanegsfarias.exception.BookNotFoundException;
import com.github.geovanegsfarias.exception.BookUnavailableException;
import com.github.geovanegsfarias.exception.UnauthorizedAccessException;
import com.github.geovanegsfarias.model.Book;
import com.github.geovanegsfarias.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ApiKeyConfigurationProperties configurationProperties;

    public BookService(BookRepository bookRepository, ApiKeyConfigurationProperties configurationProperties) {
        this.bookRepository = bookRepository;
        this.configurationProperties = configurationProperties;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findByIdOrThrowException(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public Book save(Book bookToSave) {
        assertAvailableCopiesIsValid(bookToSave);
        return bookRepository.save(bookToSave);
    }

    public void update(Book bookToUpdate) {
        assertAvailableCopiesIsValid(bookToUpdate);
        var savedBook = findByIdOrThrowException(bookToUpdate.getId());
        savedBook.setTitle(bookToUpdate.getTitle());
        savedBook.setAuthor(bookToUpdate.getAuthor());
        savedBook.setPublisher(bookToUpdate.getPublisher());
        savedBook.setTotalCopies(bookToUpdate.getTotalCopies());
        savedBook.setAvailableCopies(bookToUpdate.getAvailableCopies());
        bookRepository.save(savedBook);
    }

    public void delete(Long id) {
        var bookToDelete = findByIdOrThrowException(id);
        bookRepository.delete(bookToDelete);
    }

    public Book reserveBook(Long id, String apiKey) {
        assertApiKeyIsValid(apiKey);
        var bookToReserve = findByIdOrThrowException(id);
        assertBookIsAvailable(bookToReserve);
        bookToReserve.setAvailableCopies(bookToReserve.getAvailableCopies() - 1);
        return bookRepository.save(bookToReserve);
    }

    public Book returnBook(Long id, String apiKey) {
        assertApiKeyIsValid(apiKey);
        var bookToReturn = findByIdOrThrowException(id);
        bookToReturn.setAvailableCopies(bookToReturn.getAvailableCopies() + 1);
        return bookRepository.save(bookToReturn);
    }

    private void assertBookIsAvailable(Book book) {
        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("Book unavailable");
        }
    }

    private void assertApiKeyIsValid(String apiKey) {
        if (!apiKey.equals(configurationProperties.apiKey())) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
    }

    private void assertAvailableCopiesIsValid(Book book) {
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot be greater than total copies");
        }
    }
}