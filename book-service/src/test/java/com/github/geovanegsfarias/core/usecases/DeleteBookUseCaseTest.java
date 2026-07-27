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
class DeleteBookUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @Mock
    private FindBookByIdUseCase findBookByIdUseCase;
    @InjectMocks
    private DeleteBookUseCase deleteBookUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("deleteBook removes a book")
    @Order(1)
    void deleteBook_RemovesBook_WhenSuccessful() {
        var bookToDelete = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(bookToDelete.getId())).thenReturn(bookToDelete);
        BDDMockito.doNothing().when(bookGateway).delete(bookToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> deleteBookUseCase.deleteBook(bookToDelete.getId()));
    }

    @Test
    @DisplayName("deleteBook throws BookNotFoundException when book is not found")
    @Order(2)
    void deleteBook_ThrowsBookNotFoundException_WhenBookIsNotFound() {
        var bookToDelete = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(bookToDelete.getId())).thenThrow(new BookNotFoundException("Book not found"));

        Assertions.assertThatException()
                .isThrownBy(() -> deleteBookUseCase.deleteBook(bookToDelete.getId()))
                .isInstanceOf(BookNotFoundException.class)
                .withMessage("Book not found");
    }

}