package com.plazoleta.usuarios.infrastructure.adapters.persistence.mysql;

import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import com.plazoleta.usuarios.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userEntityMapper.modelToEntity(userModel));
    }

    @Override
    public UserModel getUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::entityToModel)
                .orElseThrow(() -> new RuntimeException(DomainConstants.USER_NOT_FOUND + id));
    }

}
