package ru.click.crm.exception.api;


import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * <p>
 *     Исключение, связанное с нарушение целостности данных.
 *     Возникает после попытки выполнения операции
 * <p>
 * Создан 01.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class ConstraintValidationException extends ValidationException {

    private static final long serialVersionUID = -986628141111910515L;

    /**
     * Описание ошибки
     */
    @Getter
    private final String message;

    /**
     * {@inheritDoc}
     * @param message
     * @param status
     */
    public ConstraintValidationException(String message, HttpStatus status) {
        super(status);
        this.message = message;
    }
}
