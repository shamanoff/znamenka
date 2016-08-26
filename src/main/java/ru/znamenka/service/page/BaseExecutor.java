package ru.znamenka.service.page;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.znamenka.api.BaseApi;
import ru.znamenka.jpa.repository.QueryFactory;

import java.util.List;
import java.util.stream.Collectors;

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
public abstract class BaseExecutor<S, T extends BaseApi> {

    /**
     * Исполнитель запросов
     */
    @Autowired
    @Qualifier("mainExecutor")
    private QueryFactory queryFactory;

    /**
     * Конвертер в представление
     */
    @Autowired
    protected Converter<S, T> converter;


    /**
     * Возвращает запрос. Если графа присутствует, то запрос будет осведомлен.
     * @return JPA запрос
     */
    protected JPAQuery<S> getQuery() {
        return queryFactory.getJpaQuery();
    }

    /**
     * Обертка для конвертера, сокращает объем кода
     * @param source результат запроса
     * @return представление
     */
    protected T toApi(S source) {
        return converter.convert(source);
    }

    /**
     * Обертка для конвертера, сокращает объем кода
     * @param source результат запроса
     * @return представление
     */
    protected List<T> toApi(List<S> source) {
        return source.stream().map(this::toApi).collect(Collectors.toList());
    }
}
