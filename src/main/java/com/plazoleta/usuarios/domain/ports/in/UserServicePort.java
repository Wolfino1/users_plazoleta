package com.plazoleta.usuarios.domain.ports.in;

import com.plazoleta.usuarios.domain.models.UserModel;

import java.util.Optional;

public interface UserServicePort {
    void save(UserModel userModel, Long roleId);
    void saveEmployee(UserModel userModel, Long ownerId);
    UserModel getUserById(Long id);
}
