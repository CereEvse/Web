package ru.web.repository;

import org.springframework.data.repository.CrudRepository;
import ru.web.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
