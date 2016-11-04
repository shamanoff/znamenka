package ru.znamenka.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@Scope(SCOPE_PROTOTYPE)
@ResponseStatus(BAD_REQUEST)
public class TestException extends RuntimeException {

    private static final long serialVersionUID = 6193194865875085145L;

    public TestException() {
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable exception) {
        super(message, exception);
    }

    public TestException(Throwable exception) {
        super(exception);
    }


}
