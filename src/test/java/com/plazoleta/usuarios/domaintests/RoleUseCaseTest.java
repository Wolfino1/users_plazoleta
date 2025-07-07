package com.plazoleta.usuarios.domaintests;

import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.ports.out.RolePersistencePort;
import com.plazoleta.usuarios.domain.usecases.RoleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleUseCaseTest {
    private RoleUseCase roleUseCase;

    @Mock
    private RolePersistencePort rolePersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleUseCase = new RoleUseCase(rolePersistencePort);
    }

    @Test
    void shouldReturnRoleById() {
        Long roleId = 1L;
        RoleModel expectedRole = new RoleModel(roleId, "ADMIN");
        when(rolePersistencePort.getByRoleById(roleId)).thenReturn(Optional.of(expectedRole));

        Optional<RoleModel> result = roleUseCase.getById(roleId);

        assertTrue(result.isPresent());
        assertEquals(expectedRole, result.get());
        verify(rolePersistencePort, times(1)).getByRoleById(roleId);
    }

    @Test
    void shouldReturnEmptyIfRoleNotFound() {
        Long roleId = 2L;
        when(rolePersistencePort.getByRoleById(roleId)).thenReturn(Optional.empty());

        Optional<RoleModel> result = roleUseCase.getById(roleId);

        assertFalse(result.isPresent());
        verify(rolePersistencePort, times(1)).getByRoleById(roleId);
    }
}

