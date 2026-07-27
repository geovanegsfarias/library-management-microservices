package com.github.geovanegsfarias.core.commons;

import com.github.geovanegsfarias.core.entities.Book;

public class BookUtils {

    public Book newBookToSave() {
        return new Book("Spring Start Here", "Laurentiu Spilca", "Manning", 10, 10);
    }

    public Book savedBook() {
        return new Book(1L, "Spring Start Here", "Laurentiu Spilca", "Manning", 10, 10);
    }
}
