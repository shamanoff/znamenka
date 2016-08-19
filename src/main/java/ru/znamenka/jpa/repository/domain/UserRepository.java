package ru.znamenka.jpa.repository.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import ru.znamenka.jpa.model.User;
import ru.znamenka.jpa.repository.QueryDslRepository;

public interface UserRepository extends QueryDslRepository<User, String> {

    @EntityGraph("user.graph")
    @Override
    User findOne(String id);
}
