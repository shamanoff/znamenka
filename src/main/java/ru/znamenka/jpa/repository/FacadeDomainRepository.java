package ru.znamenka.jpa.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.znamenka.config.BeanBucketConfig;
import ru.znamenka.jpa.model.BaseModel;
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
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> List<E> findAll(Class<E> clazz) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> List<E> findAll(Class<E> clazz, Sort sort) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll(sort);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> Page<E> findAll(Class<E> clazz, Pageable pageable) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> long count(Class<E> clazz) {
        notNull(clazz);
        return repositoryBucket.get(clazz).count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> void delete(Class<E> clazz, ID id) {
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
    public <E extends BaseModel<ID>, ID extends Serializable> List<E> saveAndFlush(Class<E> clazz, List<E> entities) {
        notNull(clazz);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        List<E> list = repository.save(entities);
        repository.flush();
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> E findOne(Class<E> clazz, ID id) {
        notNull(clazz);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        E entity = repository.findOne(id);
        if (entity == null) {
            throw new EntityNotFoundException(id.toString());
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> boolean exists(Class<E> clazz, ID id) {
        notNull(clazz);
        return repositoryBucket.get(clazz).exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> E save(Class<E> clazz, E entity) {
        notNull(clazz);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        return repository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> void deleteAll(Class<E> clazz) {
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
    public <E extends BaseModel> void deleteAllInBatch(Class<E> clazz) {
        notNull(clazz);
        repositoryBucket.get(clazz).deleteAllInBatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> E getOne(Class<E> clazz, ID id) {
        notNull(clazz);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        return repository.getOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> E saveAndFlush(Class<E> clazz, E entity) {
        notNull(clazz);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        return repository.saveAndFlush(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> List<E> save(Class<E> clazz, List<E> entities) {
        notNull(clazz);
        notNull(entities);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        return entities.stream().map(repository::save).collect(toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> E findOne(Class<E> clazz, Predicate predicate) {
        notNull(clazz);
        QueryDslRepository<E, ID> repository = repositoryBucket.get(clazz);
        return repository.findOne(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel<ID>, ID extends Serializable> List<E> findAll(Class<E> clazz, Predicate predicate) {
        notNull(clazz);
        Iterable<E> entities = repositoryBucket.get(clazz).findAll(predicate);
        return Utils.iterableToList(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> List<E> findAll(Class<E> clazz, Predicate predicate, Sort sort) {
        notNull(clazz);
        Iterable<E> entities = repositoryBucket.get(clazz).findAll(predicate, sort);
        return Utils.iterableToList(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> Page<E> findAll(Class<E> clazz, Predicate predicate, Pageable pageable) {
        notNull(clazz);
        return repositoryBucket.get(clazz).findAll(predicate, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> long count(Class<E> clazz, Predicate predicate) {
        notNull(clazz);
        return repositoryBucket.get(clazz).count(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseModel> boolean exists(Class<E> clazz, Predicate predicate) {
        notNull(clazz);
        return repositoryBucket.get(clazz).exists(predicate);
    }


}