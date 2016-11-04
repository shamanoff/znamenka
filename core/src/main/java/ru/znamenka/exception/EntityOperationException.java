package ru.znamenka.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Здесь будет описание класса
 *
 * @author Евгений Уткин (Eugene Utkin)
 *         Создан 22.06.2016
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@ResponseStatus(BAD_REQUEST)
public class EntityOperationException extends RuntimeException {
    
    private static final long serialVersionUID = -5148690280967480238L;

    public EntityOperationException() {
    }

    public EntityOperationException(String message) {
        super(message);
    }

    public EntityOperationException(String message, Throwable exception) {
        super(message, exception);
    }

    public EntityOperationException(Throwable exception) {
        super(exception);
    }
}
