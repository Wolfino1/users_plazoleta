package com.plazoleta.usuarios.infrastructure.adapters.persistence.mysql;

import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.ports.out.RolePersistencePort;
import com.plazoleta.usuarios.infrastructure.entities.RoleEntity;
import com.plazoleta.usuarios.infrastructure.mappers.RoleEntityMapper;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RolePersistencePort {

    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Optional<RoleModel> getByRoleById(Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        return roleEntity.map(roleEntityMapper::entityToModel);
    }
}
