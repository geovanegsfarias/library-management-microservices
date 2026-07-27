package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.exception.UserNotFoundException;
import com.github.geovanegsfarias.core.gateway.UserGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FindUserByEmailUseCaseTest {
    @Mock
    private UserGateway userGateway;
    @InjectMocks
    private FindUserByEmailUseCase findUserByEmailUseCase;
    private UserUtils userUtils = new UserUtils();

    @Test
    @DisplayName("findById returns a user with given id")
    @Order(1)
    void findById_ReturnsUser_WhenSuccessful() {
        var expectedUser = userUtils.savedUser();

        BDDMockito.when(userGateway.findByEmailIgnoreCase(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));

        var user = findUserByEmailUseCase.findByEmail(expectedUser.getEmail());

        org.assertj.core.api.Assertions.assertThat(expectedUser).isEqualTo(user);
    }

    @Test
    @DisplayName("findById throws UserNotFoundException when user is not found")
    @Order(2)
    void findById_ThrowsUserNotFoundException_WhenUserNotFound() {
        var savedUser = userUtils.savedUser();

        BDDMockito.when(userGateway.findByEmailIgnoreCase(savedUser.getEmail())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> findUserByEmailUseCase.findByEmail(savedUser.getEmail()))
                .isInstanceOf(UserNotFoundException.class)
                .withMessage("User not found");
    }
}