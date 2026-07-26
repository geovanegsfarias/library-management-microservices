package com.github.geovanegsfarias.infrastructure.mapper;

import com.github.geovanegsfarias.core.entities.User;
import com.github.geovanegsfarias.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);
}