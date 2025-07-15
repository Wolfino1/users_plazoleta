package com.plazoleta.usuarios.application.service.impl;

import com.plazoleta.usuarios.common.configurations.util.Constants;
import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;
import com.plazoleta.usuarios.application.mappers.UserDtoMapper;
import com.plazoleta.usuarios.application.service.UserService;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.in.UserServicePort;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import com.plazoleta.usuarios.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDtoMapper mapper;
    private final UserServicePort userServicePort;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SaveUserResponse saveOwner(SaveUserRequest req) {
        UserModel model = mapper.requestToModel(req);
        String raw       = req.password();
        String encoded   = passwordEncoder.encode(raw);
        model.setPassword(encoded, raw);
        userServicePort.save(model, DomainConstants.OWNER_ID);
        return new SaveUserResponse(
                Constants.SAVE_USER_RESPONSE_MESSAGE,
                LocalDateTime.now()
        );
    }

    @Override
    public SaveUserResponse saveEmployee(SaveUserRequest req) {
        UserModel model = mapper.requestToModel(req);
        String raw       = req.password();
        String encoded   = passwordEncoder.encode(raw);
        model.setPassword(encoded, raw);
        Long ownerId = jwtUtil.getUserIdFromSecurityContext();
        userServicePort.saveEmployee(model, ownerId);
        return new SaveUserResponse(
                Constants.SAVE_USER_RESPONSE_MESSAGE,
                LocalDateTime.now()
        );
    }

    @Override
    public SaveUserResponse saveClient(SaveUserRequest req) {
        UserModel model = mapper.requestToModel(req);
        String raw       = req.password();
        String encoded   = passwordEncoder.encode(raw);
        model.setPassword(encoded, raw);
        userServicePort.save(model, DomainConstants.CLIENT_ID);
        return new SaveUserResponse(
                Constants.SAVE_USER_RESPONSE_MESSAGE,
                LocalDateTime.now()
        );
    }

    @Override
    public UserModel getUserById(Long id) {
        return userServicePort.getUserById(id);
    }
}