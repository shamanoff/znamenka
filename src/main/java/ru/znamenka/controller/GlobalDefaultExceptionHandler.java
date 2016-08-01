package ru.znamenka.controller;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *     Глобальный обработчик ошибок.
 *     Служит для маппинга ошибок и отдаче их клиенту в стандартизированном виде
 * <p>
 * Создан 10.06.2016
 * <p>
 * Изменения:
 * <p>
 * 20.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *         Добавлено описание класса
 *     </li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler {


    /**
     * Обработчик ошибок
     * @param req запрос
     * @param e обрабатываемая ошибка
     * @return представление ошибки для клиента
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Exception defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        return e;
    }
}
