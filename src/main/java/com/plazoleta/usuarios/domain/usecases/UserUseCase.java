package com.plazoleta.usuarios.domain.usecases;

import com.plazoleta.usuarios.application.dto.response.RestaurantResponse;
import com.plazoleta.usuarios.domain.exceptions.EmptyException;
import com.plazoleta.usuarios.domain.exceptions.UnauthorizedException;
import com.plazoleta.usuarios.domain.models.RoleModel;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.in.UserServicePort;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import com.plazoleta.usuarios.infrastructure.client.RestaurantApiClient;

import java.util.Optional;

public class UserUseCase implements UserServicePort {
    private final UserPersistencePort userPersistencePort;
    private final RoleUseCase roleUseCase;
    private final RestaurantApiClient restaurantApiClient;



    public UserUseCase(UserPersistencePort userPersistencePort, RoleUseCase rolementUseCase, RestaurantApiClient restaurantApiClient) {
        this.userPersistencePort = userPersistencePort;
        this.roleUseCase = rolementUseCase;
        this.restaurantApiClient = restaurantApiClient;
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

    @Override
    public void saveEmployee(UserModel userModel, Long ownerId) {
        if (userModel.getIdRestaurant() == null) {
            throw new EmptyException(DomainConstants.RESTAURANTID_MANDATORY_FOR_EMPLOYEES);
        }

        RestaurantResponse rest = restaurantApiClient.getById(userModel.getIdRestaurant());


        if (!rest.ownerId().equals(ownerId)) {
            throw new UnauthorizedException(DomainConstants.DONT_OWN_THE_RESTAURANT);
        }

        userModel.setIdRole(DomainConstants.EMPLOYEE_ID);
        userPersistencePort.save(userModel);
    }
}

