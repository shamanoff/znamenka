package ru.znamenka.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.znamenka.jpa.model.User;
import ru.znamenka.jpa.repository.domain.UserRepository;

@Component
public class UserDao implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
       return repo.findOne(username);
    }
}
