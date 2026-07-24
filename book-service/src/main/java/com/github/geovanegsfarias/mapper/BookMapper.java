package com.github.geovanegsfarias.mapper;

import com.github.geovanegsfarias.dto.BookResponse;
import com.github.geovanegsfarias.dto.CreateBookRequest;
import com.github.geovanegsfarias.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    Book toBook(CreateBookRequest request);

    BookResponse toBookResponse(Book book);

    List<BookResponse> toBookResponseList(List<Book> books);
}