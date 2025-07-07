package com.plazoleta.usuarios.domaintests;

import com.plazoleta.usuarios.domain.exceptions.EmptyException;
import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import com.plazoleta.usuarios.domain.usecases.RoleUseCase;
import com.plazoleta.usuarios.domain.usecases.UserUseCase;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private UserUseCase userUseCase;

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private RoleUseCase roleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort, roleUseCase);
    }

    @Test
    void shouldSaveUserWhenRoleExists() {
        Long roleId = 1L;

        RoleModel roleModel = new RoleModel(roleId, "ADMIN");
        UserModel userModel = new UserModel(1L,
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1995, 5, 10),
                "juan.perez@example.com",
                "clave123",
                roleId);
        userModel.setIdRole(roleId);

        when(roleUseCase.getById(roleId)).thenReturn(Optional.of(roleModel));

        userUseCase.save(userModel);

        assertEquals(roleId, userModel.getIdRole());
        verify(userPersistencePort, times(1)).save(userModel);
    }

    @Test
    void shouldThrowExceptionWhenRoleDoesNotExist() {
        Long roleId = 2L;
        UserModel userModel = new UserModel(1L,
                "Juan",
                "Pérez",
                "123456789",
                "+573001234567",
                LocalDate.of(1995, 5, 10),
                "juan.perez@example.com",
                "clave123",
                roleId);
        userModel.setIdRole(roleId);

        when(roleUseCase.getById(roleId)).thenReturn(Optional.empty());

        EmptyException exception = assertThrows(EmptyException.class, () -> userUseCase.save(userModel));
        assertEquals(DomainConstants.ROLE_NOT_FOUND, exception.getMessage());
        verify(userPersistencePort, never()).save(any());
    }
}
