package ru.znamenka.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.znamenka.config.SecurityConfig;

import static org.mockito.Mockito.mock;


/**
 * <p>
 * <p>
 * Создан 09.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@RunWith(SpringRunner.class)
@TestConfiguration
@ContextConfiguration(classes = {SecurityConfig.class, GoogleAuthorizerTest.class})
@Slf4j
public class GoogleAuthorizerTest {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return mock(UserDetailsService.class);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void getCalendar() throws Exception {
        String pass = "q";
        log.info(passwordEncoder.encode(pass));
    }

}
