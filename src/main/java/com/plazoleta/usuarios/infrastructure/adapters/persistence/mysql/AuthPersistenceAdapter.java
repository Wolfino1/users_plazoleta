package com.plazoleta.usuarios.infrastructure.adapters.persistence.mysql;

import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.out.AuthPersistencePort;
import com.plazoleta.usuarios.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthPersistenceAdapter implements AuthPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public AuthPersistenceAdapter(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public Optional<UserModel> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::entityToModel);
    }
}