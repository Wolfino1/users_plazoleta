package com.plazoleta.usuarios.application.service.impl;

import com.plazoleta.usuarios.common.configurations.util.Constants;
import com.plazoleta.usuarios.application.dto.request.SaveUserRequest;
import com.plazoleta.usuarios.application.dto.response.SaveUserResponse;
import com.plazoleta.usuarios.application.mappers.UserDtoMapper;
import com.plazoleta.usuarios.application.service.UserService;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.in.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserServicePort userServicePort;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SaveUserResponse save(SaveUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());

        UserModel user = userDtoMapper.requestToModel(request);
        user.setPassword(encodedPassword,user.getPassword());

        userServicePort.save(user);
        return new SaveUserResponse(Constants.SAVE_USER_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}
