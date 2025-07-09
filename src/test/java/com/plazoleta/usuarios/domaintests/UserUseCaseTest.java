package com.plazoleta.usuarios.domaintests;

import com.plazoleta.usuarios.domain.exceptions.EmptyException;
import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.usecases.RoleUseCase;
import com.plazoleta.usuarios.domain.usecases.UserUseCase;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;
    @Mock
    private RoleUseCase roleUseCase;

    private UserUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new UserUseCase(userPersistencePort, roleUseCase);
    }

    @Test
    void save_WhenRoleExists_DelegatesToPersistenceAndSetsRole() {
        Long givenRoleId = 5L;
        UserModel user = new UserModel(
                1L,
                "Juan",
                "Pérez",
                "12345678",
                "+573001234567",
                LocalDate.of(1985, 6, 15),
                "juan.perez@example.com",
                "Secreto123!",
                3L
        );
        user.setIdRole(null);

        RoleModel role = new RoleModel(1L,"Santiago");
        role.setId(givenRoleId);
        when(roleUseCase.getById(givenRoleId)).thenReturn(Optional.of(role));

        useCase.save(user, givenRoleId);

        assertEquals(givenRoleId, user.getIdRole());
        verify(userPersistencePort, times(1)).save(user);
    }

    @Test
    void save_WhenRoleNotFound_ThrowsEmptyException() {
        Long missingRoleId = 99L;
        UserModel user = new UserModel(
                1L,
                "Juan",
                "Pérez",
                "12345678",
                "+573001234567",
                LocalDate.of(1985, 6, 15),
                "juan.perez@example.com",
                "Secreto123!",
                3L
        );

        when(roleUseCase.getById(missingRoleId)).thenReturn(Optional.empty());

        EmptyException ex = assertThrows(
                EmptyException.class,
                () -> useCase.save(user, missingRoleId)
        );
        assertEquals(DomainConstants.ROLE_NOT_FOUND, ex.getMessage());
        verifyNoInteractions(userPersistencePort);
    }
}
