package com.plazoleta.usuarios.application.service;

import com.plazoleta.usuarios.application.dto.request.AuthenticationRequest;
import com.plazoleta.usuarios.application.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

}
