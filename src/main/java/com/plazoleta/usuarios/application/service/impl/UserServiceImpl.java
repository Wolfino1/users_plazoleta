package com.plazoleta.usuarios.application.service.impl;

import com.plazoleta.usuarios.common.configurations.util.Constants;
import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;
import com.plazoleta.usuarios.application.mappers.UserDtoMapper;
import com.plazoleta.usuarios.application.service.UserService;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.usecases.UserUseCase;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDtoMapper mapper;
    private final UserUseCase useCase;

    @Override
    public SaveUserResponse saveOwner(SaveUserRequest req) {
        UserModel model = mapper.requestToModel(req);
        useCase.save(model, DomainConstants.OWNER_ID);
        return new SaveUserResponse(
                Constants.SAVE_USER_RESPONSE_MESSAGE,
                LocalDateTime.now()
        );
    }

    @Override
    public SaveUserResponse saveEmployee(SaveUserRequest req) {
        UserModel model = mapper.requestToModel(req);
        useCase.save(model, DomainConstants.EMPLOYEE_ID);
        return new SaveUserResponse(
                Constants.SAVE_USER_RESPONSE_MESSAGE,
                LocalDateTime.now()
        );
    }

    @Override
    public SaveUserResponse saveClient(SaveUserRequest req) {
        UserModel model = mapper.requestToModel(req);
        useCase.save(model, DomainConstants.CLIENT_ID);
        return new SaveUserResponse(
                Constants.SAVE_USER_RESPONSE_MESSAGE,
                LocalDateTime.now()
        );
    }
}