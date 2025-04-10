package ru.web.repository;

import org.springframework.data.repository.CrudRepository;
import ru.web.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
