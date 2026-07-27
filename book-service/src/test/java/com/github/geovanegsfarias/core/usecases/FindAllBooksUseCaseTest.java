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

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FindAllBooksUseCaseTest {
    @Mock
    private BookGateway bookGateway;
    @InjectMocks
    private FindAllBooksUseCase findAllBooksUseCase;
    private final BookUtils bookUtils = new BookUtils();

    @Test
    @DisplayName("findAll returns a list with all books")
    @Order(1)
    void findAll_ReturnsAllBooks_WhenSuccessful() {
        var expectedBookList = Collections.singletonList(bookUtils.savedBook());

        BDDMockito.when(bookGateway.findAll()).thenReturn(expectedBookList);

        var bookList = findAllBooksUseCase.findAllBooks();

        Assertions.assertThat(bookList).isNotNull().hasSameElementsAs(expectedBookList);
    }
}