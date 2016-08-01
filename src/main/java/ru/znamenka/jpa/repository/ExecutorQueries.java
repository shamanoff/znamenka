package ru.znamenka.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * <p>
 * Интерфейс фасада, предназначенный для обращения к методам
 * независимо от типа
 * запросов в формате {@link JPAQuery}
 * <p>
 *
 * Создан 20.06.2016
 * <p>
 * Изменения:
 * <p>
 * 29.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Удалил аннотацию {@link org.springframework.data.repository.NoRepositoryBean}</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository
public interface ExecutorQueries {

    /**
     * Инкапсулирует Создание {@link JPAQuery} запросов,
     * чтобы внешние слои не имели доступа к {@link EntityManager}'у
     * @return экземпляр {@link JPAQuery} запроса
     */
    <T> JPAQuery<T> getJpaQuery();


    /**
     * Исполяет пользовательский запрос в формате {@link JPAQuery}
     * @param query запрос
     * @return один экземпляр типа T, который является результатом выполнения запроса
     */
    <T> T executeQueryOne(JPAQuery<T> query);

    /**
     * Исполяет пользовательский запрос в формате {@link JPAQuery}
     * @param query запрос
     * @return список экземпляров типа T, которые является результатом выполнения запроса
     */
    <T> List<T> executeQueryList(JPAQuery<T> query);

    /**
     * Исполяет пользовательский запрос в формате {@link JPAQuery}.
     * Используется для реализации pagination.
     * @param query запрос
     * @param pageable параметры страницы {@link Pageable}
     * @return возвращает результат запроса в страницах
     */
    <T> Page<T> executeQueryList(JPAQuery<T> query, Pageable pageable);


}
