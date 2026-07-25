package com.github.geovanegsfarias.mapper;

import com.github.geovanegsfarias.dto.CreateUserRequest;
import com.github.geovanegsfarias.dto.UserResponse;
import com.github.geovanegsfarias.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(CreateUserRequest request);

    UserResponse toUserResponse(User user);
}