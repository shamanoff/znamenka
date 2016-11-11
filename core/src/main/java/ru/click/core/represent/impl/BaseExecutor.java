package ru.click.core.represent.impl;


import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.click.core.repository.QueryFactory;
import ru.click.core.represent.Api;
import ru.click.core.represent.Executor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Базовый класс для выполнения нестадндартных запросов.
 * Содержит минимально необходимые зависимости, а так же
 * обертки для стандартных методов.
 * <p>
 * Создан 04.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public abstract class BaseExecutor<S, T extends Api> implements Executor<S, T> {

    /**
     * Исполнитель запросов
     */
    private QueryFactory queryFactory;

    /**
     * Конвертер в представление
     */
    private Converter<S, T> converter;


    /**
     * {@inheritDoc}
     */
    @Override
    public JPAQuery<S> getQuery() {
        return queryFactory.getJpaQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T toApi(S source) {
        return converter.convert(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> toApi(List<S> source) {
        return source.stream().map(this::toApi).collect(Collectors.toList());
    }

    @Autowired
    public BaseExecutor setQueryFactory(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
        return this;
    }

    @Autowired
    public BaseExecutor setConverter(Converter<S, T> converter) {
        this.converter = converter;
        return this;
    }
}
