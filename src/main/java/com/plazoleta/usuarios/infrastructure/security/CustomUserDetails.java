package com.plazoleta.usuarios.infrastructure.security;

import com.plazoleta.usuarios.domain.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final UserModel user;

    public CustomUserDetails(UserModel user) {
        this.user = user;
    }

    public Long getRestaurantId() {
        return user.getIdRestaurant();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName;
        if (user.getIdRole() == 1L) {
            roleName = "ROLE_ADMIN";
        } else if (user.getIdRole() == 2L) {
            roleName = "ROLE_OWNER";
        }
        else if (user.getIdRole() == 3L) {
            roleName = "ROLE_EMPLOYEE";
        }else {
            roleName = "ROLE_CLIENT";
        }
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public Long getId() {
        return user.getId();
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
