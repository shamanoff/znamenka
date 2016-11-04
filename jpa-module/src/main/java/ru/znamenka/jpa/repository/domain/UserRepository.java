package ru.znamenka.jpa.repository.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.znamenka.jpa.model.User;

/**
 * CRUD репозиторий для пользователей
 */
public interface UserRepository extends CrudRepository<User, String> {

    @EntityGraph("user.graph")
    @Override
    User findOne(String id);
}
