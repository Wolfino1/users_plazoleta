package com.plazoleta.usuarios.application.mappers;


import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.domain.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    @Mapping(target = "idRestaurant", source = "idRestaurant")
    UserModel requestToModel(SaveUserRequest saveUserRequest);
}
