package com.github.geovanegsfarias.controller;

import com.github.geovanegsfarias.dto.BookResponse;
import com.github.geovanegsfarias.dto.CreateBookRequest;
import com.github.geovanegsfarias.mapper.BookMapper;
import com.github.geovanegsfarias.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
@Slf4j
public class BookController {
    private final BookService bookService;
    private final BookMapper mapper;

    public BookController(BookService bookService, BookMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.debug("Request received to list all books");

        var books = bookService.findAll();

        var bookResponseList = mapper.toBookResponseList(books);

        return ResponseEntity.ok(bookResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.debug("Request received to find book by id {}", id);

        var book = bookService.findByIdOrThrowException(id);

        var bookResponse = mapper.toBookResponse(book);

        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@RequestBody @Valid CreateBookRequest request) {
        log.debug("Request received to save book {}", request);

        var bookToSave = mapper.toBook(request);

        var savedBook = bookService.save(bookToSave);

        var bookResponse = mapper.toBookResponse(savedBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody @Valid CreateBookRequest request) {
        log.debug("Request received to update book {}", request);

        var bookToUpdate = mapper.toBook(request);

        bookToUpdate.setId(id);

        bookService.update(bookToUpdate);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("Request received to delete book by id {}", id);

        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<BookResponse> reserveBook(@PathVariable Long id) {
        log.debug("Request received to reserve a book by id {}", id);

        var reservedBook = bookService.reserveBook(id);

        var bookResponse = mapper.toBookResponse(reservedBook);

        return ResponseEntity.ok(bookResponse);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<BookResponse> returnBook(@PathVariable Long id) {
        log.debug("Request received to return a book by id {}", id);

        var returnedBook = bookService.returnBook(id);

        var bookResponse = mapper.toBookResponse(returnedBook);

        return ResponseEntity.ok(bookResponse);
    }
}
