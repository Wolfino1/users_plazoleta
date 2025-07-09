package com.plazoleta.usuarios.application.service;

import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;

public interface UserService {
    SaveUserResponse saveOwner(SaveUserRequest req);
    SaveUserResponse saveEmployee(SaveUserRequest req);
    SaveUserResponse saveClient(SaveUserRequest req);
}