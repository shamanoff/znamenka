package ru.znamenka.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * Интерфейс фабрики для создания запросов, в формате {@link JPAQuery}
 * <p>
 * Создан 20.06.2016
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository
public interface QueryFactory {

    /**
     * Инкапсулирует создание {@link JPAQuery} запросов,
     * чтобы внешние слои не имели доступа к {@link EntityManager}'у
     *
     * @return экземпляр {@link JPAQuery} запроса
     */
    <T> JPAQuery<T> getJpaQuery();

    /**
     * Исполяет пользовательский запрос в формате {@link JPAQuery}.
     * Используется для реализации pagination.
     *
     * @param query    запрос
     * @param pageable параметры страницы {@link Pageable}
     * @return возвращает результат запроса в страницах
     */
    <T> Page<T> executeQueryList(JPAQuery<T> query, Pageable pageable);


}
