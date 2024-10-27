package com.example.rentproject.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    CUSTOMER, MANAGER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
