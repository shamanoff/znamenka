package ru.click.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Репозиторий с использованием DSL запросов
 * @param <T> тип сущности
 * @param <ID> тип ее уникального идентификатора
 *
 * Создан 10.06.2016
 * <p>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository
public interface QueryDslRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {

    /**
     * @return тип бизнес-модели
     */
    Class<T> getJavaType();


}
