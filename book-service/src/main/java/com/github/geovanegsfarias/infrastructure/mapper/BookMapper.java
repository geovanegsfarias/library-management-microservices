package com.github.geovanegsfarias.infrastructure.mapper;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.infrastructure.dto.BookResponse;
import com.github.geovanegsfarias.infrastructure.dto.CreateBookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    Book toBook(CreateBookRequest request);

    BookResponse toBookResponse(Book book);

    List<BookResponse> toBookResponseList(List<Book> books);
}