package com.plazoleta.usuarios.domain.ports.in;

import com.plazoleta.usuarios.domain.models.RoleModel;

import java.util.Optional;

public interface RoleServicePort {
    Optional<RoleModel> getById(Long id);
}
