package com.github.geovanegsfarias.core.entities;

import com.github.geovanegsfarias.core.exception.BookUnavailableException;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer totalCopies;
    private Integer availableCopies;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void updateBook(String title, String author, String publisher, Integer totalCopies, Integer availableCopies) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public void reserveCopy() {
        if (this.availableCopies <= 0) {
            throw new BookUnavailableException("Book unavailable");
        }

        this.availableCopies--;
    }

    public void returnCopy() {
        this.availableCopies++;
    }
}