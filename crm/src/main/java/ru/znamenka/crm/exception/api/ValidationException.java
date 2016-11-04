package ru.znamenka.crm.exception.api;


import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * <p>
 *     Базовые ошибка уровня Web
 * <p>
 * Создан 01.07.2016
 * <p>
 * Изменения:
 * <p>
 * 04.07.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Удалил поле message</li>
 *     <li>Добавил поле с Http Status'ом, используется при обработке исключений уровня WEB</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -5175312652721251276L;

    /**
     * Статус запроса
     */
    @Getter
    protected final HttpStatus status;

    /**
     * Конструктор, который создает исключение с кодом статус ответа
     * @param status статус
     * @see HttpStatus
     */
    public ValidationException(HttpStatus status) {
        this.status = status;
    }

    /**
     * Конструктор, который создает исключение по коду статуса ответа
     * @param statusCode код (например 200, 404, etc)
     */
    public ValidationException(int statusCode) {
        this.status = HttpStatus.valueOf(statusCode);
    }



}
