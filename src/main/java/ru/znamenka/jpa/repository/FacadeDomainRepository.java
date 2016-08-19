package ru.znamenka.jpa.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.znamenka.config.BeanBucketConfig;
import ru.znamenka.util.Utils;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;


/**
 * <p>
 *     Класс представляет собой реализацию паттерна Фасад и
 *     реализует интерфес обобщенного репозитория {@link EntityRepository}
 * <p>
 *     При инициализации класса, через конструктор внедряется информация
 *     о доменных репозиториях в приложении.
 * <p>
 *     Экземпляры репозиториев хранятся в так называемой корзинке репозиториев ({@link BeanBucketConfig#repositoryBucket()}).
 *     Доступ к конкретному репозиторию осуществляется через его получение
 *     из корзинки по типу бизнес модели.
 * <p>
 *     В каждый метод передается тип бизнес-модели, на этого типа
 *     метод получает соответствующей ей доменый репозиторий и выполняет операцию
 * <p>
 *     Класс представляет смесь паттерном Фасад (Facade) и Адаптер (Adapter),
 *     так как он объявляет новый интерфейс, но по своей сути служит адаптером
 *     для интерфейса доменных репозиториев {@link QueryDslRepository}
 * <p>
 * Создан 10.06.2016
 * <p>
 * Изменения:
 * <p>
 * 20.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *      Теперь фасад реализует дополнительный интферфейс {@link QueryFactory},
 *      который позволит выполнять свои запросы.
 *     </li>
 *     <li>Заменил имя переменной suffixNameBean на postfixNameBean (this#postfixNameBean)</li>
 * </ul>
 * 21.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *         Изменил принцип нахождения бинов репозиториев.
 *         </br>
 *         Теперь все репозитории, которые привязаны к бизнес-моделям лежат в {@link BeanBucketConfig#repositoryBucket()}.
 *         </br>
 *         Состав мапы определяется в классе-конфигурации {@link BeanBucketConfig}
 *         </br>
 *         Данный способ более типобезопасный, и позвляет избегать ошибок с ненахождением репозиториев,
 *         более прозрачный и надежный, чем поиск по имени бина. И вдобавок, позволяет именовать репозитории
 *         произвольно.
 *     </li>
 *     <li>
 *         Удалена реализация интерфейса {@link QueryFactory}.
 *         </br>
 *         Теперь реализация находится в отдельном
 *         классе {@link QueryFactoryImpl}. Данное решение принято в связи с тем, что сигнатуру методов
 *         интерфейса, деклариющего метода для выполнения запросов удалось отвязать от использования Class'a
 *         в виде параметра.
 *         </br>
 *         Это позволило вынести реализацию в отдельный класс, а не расширять данный
 *         интерфейс во всех доменных репозиториях. Поэтому надобность иметь реализацию этого интерфейса в
 *         фасаде доменных репозиториев отпала.
 *     </li>
 *     <li>Изменены пакет и имя класса, так старое больше не отражает смысл</li>
 *     <li>
 *         Автопривязка бинов заменена с Field Injections на Constructor.
 *         Это позволило сделать поле с мапой репозиториев immutable.
 *     </li>
 *     <li>Изменен javaDoc</li>
 *     <li>Удалена поле с ссылкой на контекст приложения</li>
 * </ul>
 * 27.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Добавил реализацию метода {@link EntityRepository#update(Class, Object)}</li>
 *     <li>Исправил метод {@link EntityRepository#flush()} под новую сигнатуру</li>
 *     <li>Добавил проверку на пустой бакет с бинами</li>
 *     <li>Теперь класс еще выбрасывает те исключения, которые мы можем обернуть в пользовательские</li>
 * </ul>
 * 29.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Удалил аннотацию {@link org.springframework.data.repository.NoRepositoryBean}</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository("facadeRepository")
@Transactional
@SuppressWarnings("unchecked")
public class FacadeDomainRepository implements EntityRepository {

    /**
     * Хранилище репозиториев
     * @see BeanBucketConfig#repositoryBucket()
     */
    private final Map<Class, QueryDslRepository> repositoryBucket;

    @Autowired
    public FacadeDomainRepository(@Qualifier("repositoryBucket") Map<Class, QueryDslRepository> repositoryBucket) {
        notEmpty(repositoryBucket);
        this.repositoryBucket = repositoryBucket;
    }

    /**
     * {@inheritDoc}
     */
    //@Cacheable("znamenka-cache")
    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Sort sort) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll(sort);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Page<T> findAll(Class<T> clazz, Pageable pageable) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> long count(Class<T> clazz) {
        notNull(clazz);
        return repositoryBucket.get(clazz).count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, ID extends Serializable> void delete(Class<T> clazz, ID id) {
        notNull(clazz);
        try {
            repositoryBucket.get(clazz).delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(id.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> saveAndFlush(Class<T> clazz, List<T> entities) {
        notNull(clazz);
        QueryDslRepository<T, ? extends Serializable> repository = repositoryBucket.get(clazz);
        List<T> list = repository.save(entities);
        repository.flush();
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID id) {
        notNull(clazz);
        T entity = (T) repositoryBucket.get(clazz).findOne(id);
        if (entity == null) {
            throw new EntityNotFoundException(id.toString());
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, ID extends Serializable> boolean exists(Class<T> clazz, ID id) {
        notNull(clazz);
        return repositoryBucket.get(clazz).exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T save(Class<T> clazz, T entity) {
        notNull(clazz);
        return (T) repositoryBucket.get(clazz).save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void deleteAll(Class<T> clazz) {
        notNull(clazz);
        repositoryBucket.get(clazz).deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        QueryDslRepository repo = repositoryBucket.values().iterator().next();
        repo.flush();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void deleteAllInBatch(Class<T> clazz) {
        notNull(clazz);
        repositoryBucket.get(clazz).deleteAllInBatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, ID extends Serializable> T getOne(Class<T> clazz, ID id) {
        notNull(clazz);
        return (T) repositoryBucket.get(clazz).getOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T saveAndFlush(Class<T> clazz, T entity) {
        notNull(clazz);
        return (T) repositoryBucket.get(clazz).saveAndFlush(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> save(Class<T> clazz, List<T> entities) {
        notNull(clazz);
        notNull(entities);
        QueryDslRepository<T, Serializable> repository = repositoryBucket.get(clazz);
        return entities.stream().map(repository::save).collect(toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T findOne(Class<T> clazz, Predicate predicate) {
        notNull(clazz);
        return (T) repositoryBucket.get(clazz).findOne(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Predicate predicate) {
        notNull(clazz);
        Iterable<T> entities = repositoryBucket.get(clazz).findAll(predicate);
        return Utils.iterableToList(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Predicate predicate, Sort sort) {
        notNull(clazz);
        Iterable<T> entities = repositoryBucket.get(clazz).findAll(predicate, sort);
        return Utils.iterableToList(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Page<T> findAll(Class<T> clazz, Predicate predicate, Pageable pageable) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll(predicate, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> long count(Class<T> clazz, Predicate predicate) {
        notNull(clazz);
        return repositoryBucket.get(clazz).count(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean exists(Class<T> clazz, Predicate predicate) {
        notNull(clazz);
        return repositoryBucket.get(clazz).exists(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, OrderSpecifier[] orders) {
        notNull(clazz);
        notNull(orders);
        Iterable<T> entities = repositoryBucket.get(clazz).findAll(orders);
        return Utils.iterableToList(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Predicate predicate, OrderSpecifier[] orders) {
        notNull(clazz);
        Iterable<T> entities =repositoryBucket.get(clazz).findAll(predicate, orders);
        return Utils.iterableToList(entities);
    }

}