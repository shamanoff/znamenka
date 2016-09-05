package ru.znamenka.service.validation.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.znamenka.util.locale.ExtMessageSource;

@Component
@Slf4j
public class RepositoryThrowsAdvice implements ThrowsAdvice {

    @Autowired
    private BeanFactory ctx;

    @Autowired
    private ExtMessageSource messageSource;


    public void afterThrowing(RuntimeException e) throws RuntimeException {
        log.error(e.getMessage(), e);
    }
}
