package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.BookUtils;
import com.github.geovanegsfarias.core.exception.BookNotFoundException;
import com.github.geovanegsfarias.core.gateway.BookGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UpdateBookUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @Mock
    private FindBookByIdUseCase findBookByIdUseCase;
    @InjectMocks
    private UpdateBookUseCase updateBookUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("updateBook creates a book")
    @Order(1)
    void updateBook_CreatesBook_WhenSuccessful() {
        var bookToUpdate = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(bookToUpdate.getId())).thenReturn(bookToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> updateBookUseCase.updateBook(bookToUpdate));
    }

    @Test
    @DisplayName("updateBook throws IllegalArgumentException when available copies are greater than total copies")
    @Order(2)
    void updateBook_ThrowsIllegalArgumentException_WhenAvailableCopiesAreGreaterThanTotalCopies() {
        var bookToUpdate = bookUtils.savedBook();

        bookToUpdate.setAvailableCopies(bookToUpdate.getTotalCopies() + 1);

        Assertions.assertThatException()
                .isThrownBy(() -> updateBookUseCase.updateBook(bookToUpdate))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("Available copies cannot be greater than total copies");
    }

    @Test
    @DisplayName("updateBook throws BookNotFoundException when book is not found")
    @Order(3)
    void updateBook_ThrowsBookNotFoundException_WhenBookIsNotFound() {
        var bookToUpdate = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(bookToUpdate.getId())).thenThrow(new BookNotFoundException("Book not found"));

        Assertions.assertThatException()
                .isThrownBy(() -> updateBookUseCase.updateBook(bookToUpdate))
                .isInstanceOf(BookNotFoundException.class)
                .withMessage("Book not found");
    }

}