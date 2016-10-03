package ru.znamenka.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.repository.QueryFactory;
import ru.znamenka.represent.DomainApi;

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
public abstract class BaseExecutor<S, T extends DomainApi> implements Executor<S, T> {

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
