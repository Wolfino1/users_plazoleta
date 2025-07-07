package com.plazoleta.usuarios.domain.ports.in;

import com.plazoleta.usuarios.application.dto.request.AuthenticationRequest;
import com.plazoleta.usuarios.application.dto.response.AuthenticationResponse;

public interface AuthServicePort {
    AuthenticationResponse authenticate(AuthenticationRequest request);

}
