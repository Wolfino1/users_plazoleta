package com.plazoleta.usuarios.application.service;

import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;
import com.plazoleta.usuarios.domain.models.UserModel;


public interface UserService {
    SaveUserResponse saveOwner(SaveUserRequest req);
    SaveUserResponse saveEmployee(SaveUserRequest req);
    SaveUserResponse saveClient(SaveUserRequest req);
    UserModel getUserById(Long id);

}