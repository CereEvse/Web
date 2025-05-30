package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {

    USER, // Роль обычного пользователя
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
