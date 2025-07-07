package com.plazoleta.usuarios.domain.ports.out;

import com.plazoleta.usuarios.domain.models.RoleModel;

import java.util.Optional;

public interface RolePersistencePort {
    Optional<RoleModel> getByRoleById(Long id);
}
