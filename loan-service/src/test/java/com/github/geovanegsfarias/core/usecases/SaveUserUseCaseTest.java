package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.exception.UserAlreadyExistsException;
import com.github.geovanegsfarias.core.gateway.PasswordEncoderGateway;
import com.github.geovanegsfarias.core.gateway.UserGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SaveUserUseCaseTest {
    @Mock
    private UserGateway userGateway;
    @Mock
    private PasswordEncoderGateway passwordEncoderGateway;
    @InjectMocks
    private SaveUserUseCase saveUserUseCase;
    private UserUtils userUtils = new UserUtils();

    @Test
    @DisplayName("saveUser creates a user successfully")
    @Order(1)
    void saveUser_CreatesUser_WhenSuccessful() {
        var userToSave = userUtils.newUserToSave();
        var expectedSavedUser = userUtils.savedUser();
        var encodedPassword = "{bcrypt}$2a$10$yvxv.udlMjSHxePUTYeZG.i5rxBjs2rwd2kM08P7qp1GlPvWoRaCG";

        BDDMockito.when(userGateway.existsByEmailIgnoreCase(userToSave.getEmail())).thenReturn(false);
        BDDMockito.when(passwordEncoderGateway.encode(userToSave.getPassword())).thenReturn(encodedPassword);
        BDDMockito.when(userGateway.save(ArgumentMatchers.any())).thenReturn(expectedSavedUser);

        var savedUser = saveUserUseCase.save(userToSave);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isEqualTo(expectedSavedUser.getId());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(expectedSavedUser.getEmail());
    }

    @Test
    @DisplayName("saveUser throws UserAlreadyExistsException when email already exists")
    @Order(2)
    void saveUser_ThrowsUserAlreadyExistsException_WhenEmailAlreadyExists() {
        var userToSave = userUtils.newUserToSave();

        BDDMockito.when(userGateway.existsByEmailIgnoreCase(userToSave.getEmail())).thenReturn(true);

        Assertions.assertThatException()
                .isThrownBy(() -> saveUserUseCase.save(userToSave))
                .isInstanceOf(UserAlreadyExistsException.class)
                .withMessage("Email already registered");
    }
}