package com.plazoleta.usuarios.application.mappers;


import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.domain.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    UserModel requestToModel(SaveUserRequest saveUserRequest);
}
