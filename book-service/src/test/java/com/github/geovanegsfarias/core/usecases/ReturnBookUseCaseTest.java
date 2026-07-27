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
class ReturnBookUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @Mock
    private FindBookByIdUseCase findBookByIdUseCase;
    @InjectMocks
    private ReturnBookUseCase returnBookUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("returnBook returns a reserved book when successful")
    @Order(1)
    void returnBook_ReturnsBook_WhenSuccessful() {
        var savedBook = bookUtils.savedBook();
        savedBook.setAvailableCopies(9);

        BDDMockito.when(findBookByIdUseCase.findBookById(savedBook.getId())).thenReturn(savedBook);
        BDDMockito.when(bookGateway.save(savedBook)).thenReturn(savedBook);

        var returnedBook = returnBookUseCase.returnBook(savedBook.getId());

        Assertions.assertThat(returnedBook.getAvailableCopies()).isEqualTo(10);
    }

    @Test
    @DisplayName("returnBook throws BookNotFoundException when book is not found")
    @Order(2)
    void returnBook_ThrowsBookNotFoundException_WhenBookIsNotFound() {
        var savedBook = bookUtils.savedBook();

        BDDMockito.when(findBookByIdUseCase.findBookById(savedBook.getId())).thenThrow(new BookNotFoundException("Book not found"));

        Assertions.assertThatException()
                .isThrownBy(() -> returnBookUseCase.returnBook(savedBook.getId()))
                .isInstanceOf(BookNotFoundException.class)
                .withMessage("Book not found");
    }
}