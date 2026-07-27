package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.BookUtils;
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
class SaveBookUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @InjectMocks
    private SaveBookUseCase saveBookUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("saveBook creates a book")
    @Order(1)
    void saveBook_CreatesBook_WhenSuccessful() {
        var bookToSave = bookUtils.newBookToSave();
        var expectedSavedBook = bookUtils.savedBook();

        BDDMockito.when(bookGateway.save(bookToSave)).thenReturn(expectedSavedBook);

        var savedBook = saveBookUseCase.saveBook(bookToSave);

        Assertions.assertThat(savedBook).isEqualTo(expectedSavedBook);
    }

    @Test
    @DisplayName("saveBook throws IllegalArgumentException when available copies are greater than total copies")
    @Order(2)
    void saveBook_ThrowsIllegalArgumentException_WhenAvailableCopiesAreGreaterThanTotalCopies() {
        var bookToSave = bookUtils.newBookToSave();

        bookToSave.setAvailableCopies(bookToSave.getTotalCopies() + 1);

        Assertions.assertThatException()
                .isThrownBy(() -> saveBookUseCase.saveBook(bookToSave))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("Available copies cannot be greater than total copies");
    }

}