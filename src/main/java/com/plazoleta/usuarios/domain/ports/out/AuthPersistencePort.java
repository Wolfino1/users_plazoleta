package com.plazoleta.usuarios.domain.ports.out;

import com.plazoleta.usuarios.domain.models.UserModel;

import java.util.Optional;

public interface AuthPersistencePort {
    Optional<UserModel> findUserByEmail(String email);

}
