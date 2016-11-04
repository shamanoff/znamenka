package ru.znamenka.crm.service;


import com.querydsl.jpa.impl.JPAQuery;
import ru.znamenka.crm.represent.Api;

import java.util.List;

/**
 * Создан 03.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface Executor<S, T extends Api> {

    /**
     * Возвращает запрос. Если графа присутствует, то запрос будет осведомлен.
     * @return JPA запрос
     */
    JPAQuery<S> getQuery();

    /**
     * Обертка для конвертера, сокращает объем кода
     * @param source результат запроса
     * @return представление
     */
    T toApi(S source);

    /**
     * Обертка для конвертера, сокращает объем кода
     * @param source результат запроса
     * @return представление
     */
    List<T> toApi(List<S> source);
}
