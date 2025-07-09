package com.plazoleta.usuarios.domain.usecases;

import com.plazoleta.usuarios.domain.exceptions.EmptyException;
import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.in.UserServicePort;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;

import java.util.Optional;

public class UserUseCase implements UserServicePort {
    private final UserPersistencePort userPersistencePort;
    private final RoleUseCase roleUseCase;


    public UserUseCase(UserPersistencePort userPersistencePort, RoleUseCase rolementUseCase) {
        this.userPersistencePort = userPersistencePort;
        this.roleUseCase = rolementUseCase;
    }

    @Override
    public void save(UserModel userModel, Long roleId) {
        Optional<RoleModel> role = roleUseCase.getById(roleId);
        if (role.isEmpty()) {
            throw new EmptyException(DomainConstants.ROLE_NOT_FOUND);
        }
        userModel.setIdRole(roleId);
        userPersistencePort.save(userModel);
    }
}

