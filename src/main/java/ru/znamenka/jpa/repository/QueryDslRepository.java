package ru.znamenka.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.znamenka.config.BeanBucketConfig;

import java.io.Serializable;

/**
 * Репозиторий с использованием DSL запросов
 * @param <T> тип сущности
 * @param <ID> тип ее уникального идентификатора
 *
 * Создан 10.06.2016
 * <p>
 * Изменения:
 * <p>
 * 20.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Добавлены методы, которые позволяют исполнять пользовательские запросы в формате {@link JPAQuery}</li>
 * </ul>
 * 21.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *         Удален интерфейс для выполнения запросов, так как удалось сделать его универсальным и отвязать
 *         от типа бизнес-модели.
 *     </li>
 *     <li>
 *         Добавлен метод, который возвращает тип доменной модели. Используется при конфигурации корзинки.
 *         @see BeanBucketConfig
 *     </li>
 * </ul>
 * 27.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Добавлен метод {@link this#update(Object)}</li>
 * </ul>
 * 29.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Удалил аннотацию {@link org.springframework.data.repository.NoRepositoryBean}</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository
public interface QueryDslRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {

    /**
     * @return тип бизнес-модели
     */
    Class<T> getJavaType();

    /**
     * Метод для обновления бизнес-модели
     * @param entity новая версия бизнес-модели
     * @param <S> тип сущности
     * @return обновленная сущность
     * @throws javax.persistence.EntityNotFoundException
     */
    <S extends T>  S update(S entity);

}
