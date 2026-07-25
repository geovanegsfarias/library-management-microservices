package com.github.geovanegsfarias.mapper;

import com.github.geovanegsfarias.dto.*;
import com.github.geovanegsfarias.model.Loan;
import com.github.geovanegsfarias.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(CreateUserRequest request);

    UserResponse toUserResponse(User user);
}