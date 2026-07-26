package com.github.geovanegsfarias.infrastructure.mapper;

import com.github.geovanegsfarias.core.entities.Book;
import com.github.geovanegsfarias.infrastructure.persistence.BookJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookEntityMapper {
    Book toDomain(BookJpaEntity entity);
    BookJpaEntity toEntity(Book book);
}