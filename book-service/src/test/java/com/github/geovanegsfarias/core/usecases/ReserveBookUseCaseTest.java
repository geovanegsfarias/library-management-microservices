package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.BookUtils;
import com.github.geovanegsfarias.core.exception.BookNotFoundException;
import com.github.geovanegsfarias.core.exception.BookUnavailableException;
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
class ReserveBookUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @Mock
    private FindBookByIdUseCase findBookByIdUseCase;
    @InjectMocks
    private ReserveBookUseCase reserveBookUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("reserveBook returns a reserved book when successful")
    @Order(1)
    void reserveBook_ReturnsReservedBook_WhenSuccessful() {
        var savedBook = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(savedBook.getId())).thenReturn(savedBook);
        BDDMockito.when(bookGateway.save(savedBook)).thenReturn(savedBook);

        var reservedBook = reserveBookUseCase.reserveBook(savedBook.getId());

        Assertions.assertThat(reservedBook.getAvailableCopies()).isEqualTo(9);
    }

    @Test
    @DisplayName("reserveBook throws BookNotFoundException when book is not found")
    @Order(2)
    void reserveBook_ThrowsBookNotFoundException_WhenBookIsNotFound() {
        var savedBook = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(savedBook.getId())).thenThrow(new BookNotFoundException("Book not found"));

        Assertions.assertThatException()
                .isThrownBy(() -> reserveBookUseCase.reserveBook(savedBook.getId()))
                .isInstanceOf(BookNotFoundException.class)
                .withMessage("Book not found");
    }

    @Test
    @DisplayName("reserveBook throws BookUnavailableException when book is unavailable")
    @Order(3)
    void reserveBook_ThrowsBookUnavailableException_WhenBookIsUnavailable() {
        var bookToReserve = bookUtils.savedBook();
        bookToReserve.setAvailableCopies(0);

        BDDMockito.when(findBookByIdUseCase.findBookById(bookToReserve.getId())).thenReturn(bookToReserve);

        Assertions.assertThatException()
                .isThrownBy(() -> reserveBookUseCase.reserveBook(bookToReserve.getId()))
                .isInstanceOf(BookUnavailableException.class)
                .withMessage("Book unavailable");
    }

}