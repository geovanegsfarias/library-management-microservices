package com.github.geovanegsfarias.infrastructure.presentation;

import com.github.geovanegsfarias.core.usecases.*;
import com.github.geovanegsfarias.infrastructure.dto.BookResponse;
import com.github.geovanegsfarias.infrastructure.dto.CreateBookRequest;
import com.github.geovanegsfarias.infrastructure.mapper.BookMapper;
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
    private final FindAllBooksUseCase findAllBooksUseCase;
    private final FindBookByIdUseCase findBookByIdUseCase;
    private final SaveBookUseCase saveBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;
    private final ReserveBookUseCase reserveBookUseCase;
    private final ReturnBookUseCase returnBookUseCase;
    private final BookMapper mapper;

    public BookController(FindAllBooksUseCase findAllBooksUseCase, FindBookByIdUseCase findBookByIdUseCase, SaveBookUseCase saveBookUseCase, UpdateBookUseCase updateBookUseCase, DeleteBookUseCase deleteBookUseCase, ReserveBookUseCase reserveBookUseCase, ReturnBookUseCase returnBookUseCase, BookMapper mapper) {
        this.findAllBooksUseCase = findAllBooksUseCase;
        this.findBookByIdUseCase = findBookByIdUseCase;
        this.saveBookUseCase = saveBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
        this.reserveBookUseCase = reserveBookUseCase;
        this.returnBookUseCase = returnBookUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "List books")
    @ApiResponse(responseCode = "200", description = "Books returned")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.debug("Request received to list all books");

        var books = findAllBooksUseCase.findAllBooks();

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

        var book = findBookByIdUseCase.findBookById(id);

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

        var savedBook = saveBookUseCase.saveBook(bookToSave);

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

        updateBookUseCase.updateBook(bookToUpdate);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    @ApiResponse(responseCode = "204", description = "Book deleted")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.debug("Request received to delete book by id {}", id);

        deleteBookUseCase.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reserve")
    @Operation(summary = "Reserve book")
    @ApiResponse(responseCode = "200", description = "Book reserved")
    @ApiResponse(responseCode = "400", description = "Book unavailable", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "403", description = "Unauthorized access", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<BookResponse> reserveBook(@PathVariable Long id) {
        log.debug("Request received to reserve a book by id {}", id);

        var reservedBook = reserveBookUseCase.reserveBook(id);

        var bookResponse = mapper.toBookResponse(reservedBook);

        return ResponseEntity.ok(bookResponse);
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return book")
    @ApiResponse(responseCode = "200", description = "Reserved book returned")
    @ApiResponse(responseCode = "403", description = "Unauthorized access", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<BookResponse> returnBook(@PathVariable Long id) {
        log.debug("Request received to return a book by id {}", id);

        var returnedBook = returnBookUseCase.returnBook(id);

        var bookResponse = mapper.toBookResponse(returnedBook);

        return ResponseEntity.ok(bookResponse);
    }
}
