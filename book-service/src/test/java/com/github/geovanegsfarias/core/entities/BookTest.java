package com.github.geovanegsfarias.core.entities;

import com.github.geovanegsfarias.core.commons.BookUtils;
import com.github.geovanegsfarias.core.exception.BookUnavailableException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookTest {

    private final BookUtils bookUtils = new BookUtils();

    @Test
    @Order(1)
    void reserveCopy_ReservesCopy_WhenSuccessful() {
        var book = bookUtils.savedBook();

        book.reserveCopy();

        Assertions.assertThat(book.getAvailableCopies()).isEqualTo(9);
    }

    @Test
    @Order(2)
    void reserveCopy_ThrowsBookUnavailableException_WhenAvailableCopiesIsLessThanOrEqualToZero() {
        var book = bookUtils.savedBook();

        book.setAvailableCopies(0);

        Assertions.assertThatException()
                .isThrownBy(() -> book.reserveCopy())
                .isInstanceOf(BookUnavailableException.class)
                .withMessage("Book unavailable");
    }

    @Test
    @Order(3)
    void returnCopy_ReturnsCopy_WhenSuccessful() {
        var book = bookUtils.savedBook();

        book.returnCopy();

        Assertions.assertThat(book.getAvailableCopies()).isEqualTo(11);
    }

}