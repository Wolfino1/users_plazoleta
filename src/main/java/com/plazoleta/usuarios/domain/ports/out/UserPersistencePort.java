package com.plazoleta.usuarios.domain.ports.out;

import com.plazoleta.usuarios.domain.models.UserModel;

public interface UserPersistencePort {
    void save(UserModel userModel);
}
