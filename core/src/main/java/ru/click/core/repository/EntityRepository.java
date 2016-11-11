package ru.click.core.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.click.core.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Интерфейс обобщенного репозитория
 * <p>
 * Создан 08.06.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface EntityRepository {
    /**
     * @param clazz  класс бизнес-модели
     * @param entity коллекция сущностей
     * @param <E>    тип бизнес-модели
     * @return сущность, которая была сохранена
     * @see CrudRepository#save(Object)
     */
    <E extends BaseModel<ID>, ID extends Serializable> E save(Class<E> clazz, E entity);

    /**
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return объект бизнес-модели с заданным id
     * @see CrudRepository#findOne(Serializable)
     */
    <E extends BaseModel<ID>, ID extends Serializable> E findOne(Class<E> clazz, ID id);

    /**
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return true, если хранится, иначе false
     * @see CrudRepository#exists(Serializable)
     */
    <E extends BaseModel<ID>, ID extends Serializable> boolean exists(Class<E> clazz, ID id);

    /**
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @return список сущностей
     * @see CrudRepository#findAll()
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> findAll(Class<E> clazz);

    /**
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param sort  сортировка {@link Sort}
     * @return список сущностей
     * @see PagingAndSortingRepository#findAll(Sort)
     */
    <E extends BaseModel> List<E> findAll(Class<E> clazz, Sort sort);

    /**
     * @param clazz    класс бизнес-модели
     * @param <E>      тип бизнес-модели
     * @param pageable объект, содержащий информацию о номере странице и количестве записей на этой странице
     * @return станица, которая содержит данные и служебную информацию
     * @see PagingAndSortingRepository#findAll(Pageable)
     * @see Page
     */
    <E extends BaseModel> Page<E> findAll(Class<E> clazz, Pageable pageable);

    /**
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @return количество сущностей
     * @see CrudRepository#count()
     */
    <E extends BaseModel> long count(Class<E> clazz);

    /**
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип идентификатора
     * @see CrudRepository#delete(Serializable)
     */
    <E extends BaseModel<ID>, ID extends Serializable> void delete(Class<E> clazz, ID id);

    /**
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @see CrudRepository#deleteAll()
     */
    <E extends BaseModel> void deleteAll(Class<E> clazz);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     */
    void flush();

    /**
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @see org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(Iterable)
     */
    <E extends BaseModel> void deleteAllInBatch(Class<E> clazz);

    /**
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return ссылка на объект с данным идентификатором.
     * @see org.springframework.data.jpa.repository.JpaRepository#getOne(Serializable)
     */
    <E extends BaseModel<ID>, ID extends Serializable> E getOne(Class<E> clazz, ID id);

    /**
     * @param clazz  класс бизнес-модели
     * @param entity коллекция сущностей
     * @param <E>    тип бизнес-модели
     * @return сущность, которая была сохранена
     * @see org.springframework.data.jpa.repository.JpaRepository#saveAndFlush(Object)
     */
    <E extends BaseModel<ID>, ID extends Serializable> E saveAndFlush(Class<E> clazz, E entity);

    /**
     * @param clazz    класс бизнес-модели
     * @param entities коллекция сущностей {@link Iterable}
     * @param <E>      тип бизнес-модели
     * @return список сущностей
     * @see CrudRepository#save(Iterable)
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> saveAndFlush(Class<E> clazz, List<E> entities);

    /**
     * @param clazz    класс бизнес-модели
     * @param entities коллекция сущностей {@link Iterable}
     * @param <E>      тип бизнес-модели
     * @return сохраненные бизнес-модели
     * @see CrudRepository#save(Iterable)
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> save(Class<E> clazz, List<E> entities);

    /**
     * @param clazz     класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>       тип бизнес-модели
     * @return сущность
     * @see QueryDslPredicateExecutor#findOne(Predicate)
     */
    <E extends BaseModel<ID>, ID extends Serializable> E findOne(Class<E> clazz, Predicate predicate);

    /**
     * @param clazz     класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>       тип бизнес-модели
     * @return найденные бизнес-модели
     * @see QueryDslPredicateExecutor#findAll(Predicate)
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> findAll(Class<E> clazz, Predicate predicate);

    /**
     * @param clazz     класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param sort      заданная сортировка {@link Sort}
     * @param <E>       тип бизнес-модели
     * @return найденные бизнес-модели
     * @see QueryDslPredicateExecutor#findAll(Predicate, Sort)
     */
    <E extends BaseModel> List<E> findAll(Class<E> clazz, Predicate predicate, Sort sort);

    /**
     * @param clazz     класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param pageable  параметры страницы {@link Pageable}
     * @param <E>       тип бизнес-модели
     * @return страница, содержащая бизнес-модели
     * @see QueryDslPredicateExecutor#findAll(Predicate, Pageable)
     */
    <E extends BaseModel> Page<E> findAll(Class<E> clazz, Predicate predicate, Pageable pageable);

    /**
     * @param clazz     класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>       тип бизнес-модели
     * @return количество сущностей, удолетворяющих предикату
     * @see QueryDslPredicateExecutor#count(Predicate)
     */
    <E extends BaseModel> long count(Class<E> clazz, Predicate predicate);

    /**
     * @param clazz     класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>       тип бизнес-модели
     * @return true, если существуют бизнес-модели, удолетворяющие предикату
     * @see QueryDslPredicateExecutor#exists(Predicate)
     */
    <E extends BaseModel> boolean exists(Class<E> clazz, Predicate predicate);

}
       
    

