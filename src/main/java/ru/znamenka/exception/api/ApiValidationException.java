package ru.znamenka.exception.api;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * <p>
 *     Исключение, которое выбрасывается, если при валидации
 *     представления возникли ошибки
 * <p>
 * Создан 04.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class ApiValidationException extends ValidationException {

    private static final long serialVersionUID = 1541103228447174340L;

    /**
     * Список описаний ошибок валидации
     */
    @Getter
    private final List<String> messages;

    /**
     * Создает исключение на основе списка описаний ошибок и http статуса ответа
     * @param messages список сообщений
     * @param status http статус
     */
    public ApiValidationException(List<String> messages, HttpStatus status) {
        super(status);
       this.messages = messages;
    }

    /**
     * Создает исключение на основе описания ошибки и http статуса ответа
     * @param message сообщение
     * @param status http статус
     */
    public ApiValidationException(String message, HttpStatus status) {
        super(status);
        this.messages = singletonList(message);
    }
}
