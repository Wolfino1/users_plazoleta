package com.plazoleta.usuarios.domain.usecases;

import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.ports.in.RoleServicePort;
import com.plazoleta.usuarios.domain.ports.out.RolePersistencePort;

import java.util.Optional;

public class RoleUseCase implements RoleServicePort {
    private final RolePersistencePort rolePersistencePort;

    public RoleUseCase(RolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public Optional<RoleModel> getById(Long id) {
        return rolePersistencePort.getByRoleById(id);
    }
}
