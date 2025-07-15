package com.plazoleta.usuarios.common.configurations.beans;

import com.plazoleta.usuarios.domain.ports.in.RoleServicePort;
import com.plazoleta.usuarios.domain.ports.in.UserServicePort;
import com.plazoleta.usuarios.domain.ports.out.RolePersistencePort;
import com.plazoleta.usuarios.domain.ports.out.UserPersistencePort;
import com.plazoleta.usuarios.domain.usecases.RoleUseCase;
import com.plazoleta.usuarios.domain.usecases.UserUseCase;
import com.plazoleta.usuarios.infrastructure.adapters.persistence.mysql.RolePersistenceAdapter;
import com.plazoleta.usuarios.infrastructure.adapters.persistence.mysql.UserPersistenceAdapter;
import com.plazoleta.usuarios.infrastructure.client.RestaurantApiClient;
import com.plazoleta.usuarios.infrastructure.mappers.RoleEntityMapper;
import com.plazoleta.usuarios.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.RoleRepository;
import com.plazoleta.usuarios.infrastructure.repositories.mysql.UserRepository;
import com.plazoleta.usuarios.infrastructure.security.CustomUserDetailsService;
import com.plazoleta.usuarios.infrastructure.security.JwtAuthenticationFilter;
import com.plazoleta.usuarios.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final RestaurantApiClient restaurantApiClient;

    @Bean
    public UserPersistencePort userPersistencePort() {
        return new UserPersistenceAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public RolePersistencePort rolePersistencePort(){
        return new RolePersistenceAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public RoleUseCase roleUseCase() {
        return new RoleUseCase(rolePersistencePort());
    }

    @Bean
    public UserUseCase userUseCase() {
        return new UserUseCase(userPersistencePort(), roleUseCase(), restaurantApiClient);
    }

    @Bean
    public UserServicePort userServicePort() {
        return userUseCase();
    }

    @Bean
    public RoleServicePort roleServicePort() {
        return roleUseCase();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain publicApiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(
                        "/api/v1/auth/login",
                        "/api/v1/restaurants/get",
                        //"/api/v1/user/owner",
                        "/api/v1/user/client",
                        "/api/v1/user/{id}"
                )
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(a -> a.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain protectedApiChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        http
                .securityMatcher("/api/v1/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(a -> a
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/owner").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/employee").hasRole("OWNER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
