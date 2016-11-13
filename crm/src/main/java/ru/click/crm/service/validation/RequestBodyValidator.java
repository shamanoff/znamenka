package ru.click.crm.service.validation;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import ru.click.crm.exception.api.ApiValidationException;
import ru.click.crm.util.locale.ExtMessageSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * <p>
 *     Валидатор уровня веб.
 * <p>
 * Создан 04.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public abstract class RequestBodyValidator<T> {

    /**
     * Код кода сообщения по умолчанию. Если в результате валидации возникает
     * ошибка и кода ее сообщения нет в наших ресурсах, отдаем сообщение по умолчанию
     */
    private static final String DEFAULT_ERROR_CODE_MESSAGE = "api.default.validation.error";

    /**
     * <p>
     * Источник сообщений. Используется для получения сообщений по коду
     * из файла ресурсов (static/location/messages.properties).
     * <p>
     * Уровень доступа protected необходим, чтобы наследники имели доступ
     * к источнику сообщений для большей гибкости
     *
     */
    @Autowired
    protected ExtMessageSource messageSource;

    /**
     * Контейнер бинов
     */
    @Autowired
    protected BeanFactory ctx;

    /**
     * Кастомный валидатор. По умолчанию равен {@literal null}.
     * Каждый наследник может использовать валидатор, установив
     * значение через {@link RequestBodyValidator#setValidator(Validator)}
     */
    private Validator validator;

    /**
     * Метод отправляет представление на дополнительную валидацию.
     * Если при валидации возникли ошибки, то результат валидации передается
     * в {@link RequestBodyValidator#handleErrors(BindingResult)}.
     * @param api представление
     * @param bindingResult результат валидации средствами Spring MVC
     */
    protected void prepare(T api, BindingResult bindingResult) {
        validate(api, bindingResult);
        if (bindingResult.hasErrors()) {
            handleErrors(bindingResult);
        }
    }

    /**
     * Метод валидирует представление с помощью
     * кастомного валидатора, если он установлен.
     * @param api представление
     * @param bindingResult результат валидации кастомного валидатора
     */
    private void validate(T api, BindingResult bindingResult) {
        if (validator != null) {
            validator.validate(api, bindingResult);
        }
    }

    /**
     * Обработчик ошибок валидации. Может быть переопределен
     * наследниками для большей гибкости.
     * @param bindingResult результат валидации
     */
    protected void handleErrors(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        List<String> messages = errors
                .stream()
                .map(ObjectError::getDefaultMessage) //достаем код сообщения
                .map(mes -> messageSource.getMessage(mes, DEFAULT_ERROR_CODE_MESSAGE)) /* забираем сообщение из
                источника, если не найдено, используем код сообщения по умолчанию */
                .collect(Collectors.toList());
        throw ctx.getBean(ApiValidationException.class, messages, BAD_REQUEST);
    }

    /**
     * Getter для валидатора
     * @return кастомный валидатор
     */
    protected Validator getValidator() {
        return validator;
    }

    /**
     * Setter для валидотора.
     * Позволяет наследникам устанавливать свой валидатор дополнительно
     * @param validator валидатор
     */
    protected void setValidator(Validator validator) {
        this.validator = validator;
    }
}
