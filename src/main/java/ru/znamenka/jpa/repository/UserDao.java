package ru.znamenka.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.znamenka.jpa.model.User;
import ru.znamenka.jpa.repository.domain.UserRepository;

import static org.springframework.util.Assert.notNull;

/**
 * Реализация {@link UserDetailsService}
 * <p>
 * Создан 21.06.2016
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class UserDao implements UserDetailsService {

    /**
     * Репозитрий для операций с юзерами
     */
    private final UserRepository repo;

    /**
     * Констурктор для внедрения зависимостей
     * @param repo репозиторий юзеров
     */
    @Autowired
    public UserDao(UserRepository repo) {
        notNull(repo);
        this.repo = repo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repo.findOne(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }
}
