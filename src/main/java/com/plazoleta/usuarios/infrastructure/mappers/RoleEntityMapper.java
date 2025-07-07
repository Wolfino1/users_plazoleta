package com.plazoleta.usuarios.infrastructure.mappers;


import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.infrastructure.entities.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface RoleEntityMapper {
    RoleModel entityToModel(RoleEntity roleEntity);

}
