package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByLogin(String login);

    void registration(String login, String password, String surname, String name, String middle_name );

}
