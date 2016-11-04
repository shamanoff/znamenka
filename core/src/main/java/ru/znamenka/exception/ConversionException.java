package ru.znamenka.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * <p>
 *     Ошибка возникшая при конвертации данных в представление
 * <p>
 * Создан 21.06.2016
 * <p>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
@Scope(SCOPE_PROTOTYPE)
@ResponseStatus(BAD_REQUEST)
public class ConversionException extends RuntimeException {

    private static final long serialVersionUID = 702292872913260173L;

    /**
     * {@inheritDoc}
     */
    public ConversionException() {
    }

    /**
     * {@inheritDoc}
     * @param message
     */
    public ConversionException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     * @param message
     * @param exception
     */
    public ConversionException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * {@inheritDoc}
     * @param exception
     */
    public ConversionException(Throwable exception) {
        super(exception);
    }
}
