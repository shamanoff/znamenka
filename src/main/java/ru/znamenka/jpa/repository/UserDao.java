package ru.znamenka.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.znamenka.jpa.model.User;

import javax.persistence.EntityManager;

import static ru.znamenka.jpa.model.QUser.user;

@Component
public class UserDao implements UserDetailsService {

    @Autowired
    private QueryFactory factory;

    @Autowired
    private EntityManager em;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        JPAQuery<User> query = factory.getJpaQuery();
        query.from(user).where(user.username.eq(username));
        return query.fetchOne();
    }

    public void save(User user) {
        em.persist(user);
    }
}
