package ru.web.model;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    USER, // Роль обычного пользователя
    EMPLOYEE, // Роль разработчика
    CUSTOMER,  // Роль заказчика
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
