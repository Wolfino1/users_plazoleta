package com.plazoleta.usuarios.application.service.impl;

import com.plazoleta.usuarios.application.dto.request.AuthenticationRequest;
import com.plazoleta.usuarios.application.dto.response.AuthenticationResponse;
import com.plazoleta.usuarios.application.service.AuthService;
import com.plazoleta.usuarios.domain.ports.in.AuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthServicePort authServicePort;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return authServicePort.authenticate(request);
    }
}