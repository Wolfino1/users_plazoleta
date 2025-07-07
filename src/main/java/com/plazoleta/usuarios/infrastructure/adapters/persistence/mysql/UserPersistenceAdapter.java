package com.plazoleta.usuarios.infrastructure.adapters.persistence.mysql;


import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import com.plazoleta.usuarios.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
