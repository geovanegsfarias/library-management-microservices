package com.github.geovanegsfarias.controller;

import com.github.geovanegsfarias.dto.BookResponse;
import com.github.geovanegsfarias.dto.CreateBookRequest;
import com.github.geovanegsfarias.mapper.BookMapper;
import com.github.geovanegsfarias.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
@Slf4j
@Tag(name = "Book")
public class BookController {
    private final BookService bookService;
    private final BookMapper mapper;

    public BookController(BookService bookService, BookMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List books")
    @ApiResponse(responseCode = "200", description = "Books returned")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.debug("Request received to list all books");

        var books = bookService.findAll();

        var bookResponseList = mapper.toBookResponseList(books);

        return ResponseEntity.ok(bookResponseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book")
    @ApiResponse(responseCode = "200", description = "Book returned")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.debug("Request received to find book by id {}", id);

        var book = bookService.findByIdOrThrowException(id);

        var bookResponse = mapper.toBookResponse(book);

        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping
    @Operation(summary = "Create book")
    @ApiResponse(responseCode = "201", description = "Book created")
    @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "400", description = "Available copies cannot be greater than total copies", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<BookResponse> saveBook(@RequestBody @Valid CreateBookRequest request) {
        log.debug("Request received to save book {}", request);

        var bookToSave = mapper.toBook(request);

        var savedBook = bookService.save(bookToSave);

        var bookResponse = mapper.toBookResponse(savedBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book")
    @ApiResponse(responseCode = "204", description = "Book updated")
    @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "400", description = "Available copies cannot be greater than total copies", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody @Valid CreateBookRequest request) {
        log.debug("Request received to update book {}", request);

        var bookToUpdate = mapper.toBook(request);

        bookToUpdate.setId(id);

        bookService.update(bookToUpdate);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    @ApiResponse(responseCode = "204", description = "Book deleted")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("Request received to delete book by id {}", id);

        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reserve")
    @Operation(summary = "Reserve book")
    @ApiResponse(responseCode = "200", description = "Book reserved")
    @ApiResponse(responseCode = "400", description = "Book unavailable", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "403", description = "Unauthorized access", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<BookResponse> reserveBook(@PathVariable Long id, @RequestHeader("X-Api-Key") String apiKey) {
        log.debug("Request received to reserve a book by id {}", id);

        var reservedBook = bookService.reserveBook(id, apiKey);

        var bookResponse = mapper.toBookResponse(reservedBook);

        return ResponseEntity.ok(bookResponse);
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return book")
    @ApiResponse(responseCode = "200", description = "Reserved book returned")
    @ApiResponse(responseCode = "403", description = "Unauthorized access", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<BookResponse> returnBook(@PathVariable Long id, @RequestHeader("X-Api-Key") String apiKey) {
        log.debug("Request received to return a book by id {}", id);

        var returnedBook = bookService.returnBook(id, apiKey);

        var bookResponse = mapper.toBookResponse(returnedBook);

        return ResponseEntity.ok(bookResponse);
    }
}
