package ru.znamenka.service.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.znamenka.api.BaseApi;
import ru.znamenka.jpa.repository.QueryFactory;

/**
 * <p>
 *     Базовый класс для выполнения нестадндартных запросов.
 *     Содержит минимально необходимые зависимости, а так же
 *     обертки для стандартных методов.
 * <p>
 * Создан 04.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class BaseExecutor<S, T extends BaseApi> {

    /**
     * Исполнитель запросов
     */
    @Autowired
    @Qualifier("mainExecutor")
    protected QueryFactory executor;

    /**
     * Конвертер в представление
     */
    //@Autowired
    protected Converter<S, T> converter;


    /**
     * Возвращает запрос. Если графа присутствует, то запрос будет осведомлен.
     * @return JPA запрос
     */
    protected JPAQuery<S> getQuery() {
        return executor.getJpaQuery();
    }

    /**
     * Обертка для конвертера, сокращает объем кода
     * @param source результат запроса
     * @return представление
     */
    protected T convert(S source) {
        return converter.convert(source);
    }
}
