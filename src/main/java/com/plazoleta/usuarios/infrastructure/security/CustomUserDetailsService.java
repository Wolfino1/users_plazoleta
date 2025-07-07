package com.plazoleta.usuarios.infrastructure.security;

import com.plazoleta.usuarios.domain.util.constants.DomainConstants;
import com.plazoleta.usuarios.domain.models.UserModel;
import com.plazoleta.usuarios.infrastructure.entities.UserEntity;
import com.plazoleta.usuarios.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public CustomUserDetailsService(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(DomainConstants.USER_NOT_FOUND));
        UserModel userModel = userEntityMapper.entityToModel(userEntity);
        return new CustomUserDetails(userModel);
    }
}
