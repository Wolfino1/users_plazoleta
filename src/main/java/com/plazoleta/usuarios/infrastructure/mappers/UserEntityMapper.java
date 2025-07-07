package com.plazoleta.usuarios.infrastructure.mappers;

import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.infrastructure.entities.RoleEntity;
import com.plazoleta.usuarios.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "role.id", source = "idRole")
    UserEntity modelToEntity(UserModel model);

    @Mapping(target = "idRole", source = "role.id")
    UserModel entityToModel(UserEntity entity);

    default RoleEntity mapRole(Long idRole) {
        if (idRole == null) {
            return null;
        }
        RoleEntity role = new RoleEntity();
        role.setId(idRole);
        return role;
    }
}
