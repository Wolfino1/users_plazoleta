package com.plazoleta.usuarios.domaintests;

import com.plazoleta.usuarios.application.dto.request.AuthenticationRequest;
import com.plazoleta.usuarios.application.dto.response.AuthenticationResponse;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.out.AuthPersistencePort;
import com.plazoleta.usuarios.domain.usecases.AuthUseCase;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import com.plazoleta.usuarios.infrastructure.security.CustomUserDetails;
import com.plazoleta.usuarios.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthUseCaseTest {

    @Test
    void authenticate_ShouldReturnToken_WhenCredentialsAreValid() {
        AuthPersistencePort authPersistencePort = mock(AuthPersistencePort.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AuthUseCase authUseCase = new AuthUseCase(authPersistencePort, passwordEncoder, jwtUtil);

        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "$2a$10$abc";

        UserModel user = new UserModel(1L, "Juan", "Pérez", "123456789", "+573001112233", LocalDate.of(1995, 5, 20), "juan@example.com", "hashedPassword123", 2L);
        user.setEmail(email);
        user.setPassword(encodedPassword, password);

        AuthenticationRequest request = new AuthenticationRequest(email, password);
        String expectedToken = "mocked-jwt-token";

        when(authPersistencePort.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(any(CustomUserDetails.class))).thenReturn(expectedToken);

        AuthenticationResponse response = authUseCase.authenticate(request);

        assertNotNull(response);
        assertEquals(expectedToken, response.token());
    }

    @Test
    void authenticate_ShouldThrowException_WhenUserNotFound() {
        AuthPersistencePort authPersistencePort = mock(AuthPersistencePort.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AuthUseCase authUseCase = new AuthUseCase(authPersistencePort, passwordEncoder, jwtUtil);

        String email = "nonexistent@example.com";
        AuthenticationRequest request = new AuthenticationRequest(email, "password");

        when(authPersistencePort.findUserByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authUseCase.authenticate(request)
        );

        assertEquals(DomainConstants.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void authenticate_ShouldThrowException_WhenPasswordDoesNotMatch() {
        AuthPersistencePort authPersistencePort = mock(AuthPersistencePort.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AuthUseCase authUseCase = new AuthUseCase(authPersistencePort, passwordEncoder, jwtUtil);

        String email = "user@example.com";
        String password = "wrongpass";
        String encodedPassword = "$2a$10$encoded";

        UserModel user = new UserModel(1L, "Juan", "Pérez", "123456789",
                "+573001112233", LocalDate.of(1995, 5, 20), "juan@example.com", "hashedPassword123", 2L);
        user.setEmail(email);
        user.setPassword(encodedPassword, password);

        AuthenticationRequest request = new AuthenticationRequest(email, password);

        when(authPersistencePort.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authUseCase.authenticate(request)
        );

        assertEquals(DomainConstants.INVALID_CREDENTIALS, exception.getMessage());
    }
}

