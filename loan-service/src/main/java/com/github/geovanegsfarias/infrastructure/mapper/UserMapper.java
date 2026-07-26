package com.github.geovanegsfarias.infrastructure.mapper;

import com.github.geovanegsfarias.core.entities.User;
import com.github.geovanegsfarias.infrastructure.dto.CreateUserRequest;
import com.github.geovanegsfarias.infrastructure.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(CreateUserRequest request);

    UserResponse toUserResponse(User user);
}