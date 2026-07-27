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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FindBookByIdUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @InjectMocks
    private FindBookByIdUseCase findBookByIdUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("findById returns a book with given id")
    @Order(1)
    void findById_ReturnsBook_WhenSuccessful() {
        var expectedBook = bookUtils.savedBook();

        BDDMockito.when(bookGateway.findById(expectedBook.getId())).thenReturn(Optional.of(expectedBook));

        var book = findBookByIdUseCase.findBookById(expectedBook.getId());

        Assertions.assertThat(expectedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("findById throws BookNotFoundException when book is not found")
    @Order(2)
    void findById_ThrowsBookNotFoundException_WhenBookNotFound() {
        var savedBook = bookUtils.savedBook();

        BDDMockito.when(bookGateway.findById(savedBook.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> findBookByIdUseCase.findBookById(savedBook.getId()))
                .isInstanceOf(BookNotFoundException.class)
                .withMessage("Book not found");
    }

}