package ru.click.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.click.core.model.User;
import ru.click.core.repository.domain.UserRepository;

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
            User user = repo.findOne(username);
            if (user.getTrainer() != null && user.getName() == null) {
                user.setName(user.getTrainer().getName());
            }
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }
}
