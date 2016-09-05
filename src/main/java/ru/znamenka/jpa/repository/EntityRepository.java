package ru.znamenka.jpa.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.znamenka.jpa.model.BaseModel;

import java.io.Serializable;
import java.util.List;
/**
 * <p>
 *     Интерфейс обобщенного репозитория
 * <p>
 * Создан 08.06.2016
 * <p>
 * Изменения:
 * <p>
 * 27.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>добавлен метод {@link this#update(Class, Object)}</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface EntityRepository {
    /**
     * @see CrudRepository#save(Object)
     * @param clazz  класс бизнес-модели
     * @param entity коллекция сущностей
     * @param <E>    тип бизнес-модели
     * @return сущность, которая была сохранена
     */
    <E extends BaseModel<ID>, ID extends Serializable> E save(Class<E> clazz, E entity);

    /**
     * @see CrudRepository#findOne(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return объект бизнес-модели с заданным id
     */
    <E extends BaseModel<ID>, ID extends Serializable> E findOne(Class<E> clazz, ID id);

    /**
     * @see CrudRepository#exists(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return true, если хранится, иначе false
     */
    <E extends BaseModel<ID>, ID extends Serializable> boolean exists(Class<E> clazz, ID id);

    /**
     * @see CrudRepository#findAll()
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @return список сущностей
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> findAll(Class<E> clazz);

    /**
     * @see PagingAndSortingRepository#findAll(Sort)
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param sort сортировка {@link Sort}
     * @return список сущностей
     * @throws RuntimeException
     */
    <E extends BaseModel> List<E> findAll(Class<E> clazz, Sort sort);

    /**
     * @see PagingAndSortingRepository#findAll(Pageable)
     * @see Page
     * @param clazz    класс бизнес-модели
     * @param <E>      тип бизнес-модели
     * @param pageable объект, содержащий информацию о номере странице и количестве записей на этой странице
     * @return станица, которая содержит данные и служебную информацию
     */
    <E extends BaseModel> Page<E> findAll(Class<E> clazz, Pageable pageable);

    /**
     * @see CrudRepository#count()
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @return  количество сущностей
     */
    <E extends BaseModel> long count(Class<E> clazz);

    /**
     * @see CrudRepository#delete(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип идентификатора
     */
    <E extends BaseModel<ID>, ID extends Serializable> void delete(Class<E> clazz, ID id);

    /**
     * @see CrudRepository#deleteAll()
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     */
    <E extends BaseModel> void deleteAll(Class<E> clazz);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     */
    void flush();

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(Iterable)
     * @param clazz класс бизнес-модели
     * @param <E>   тип бизнес-модели
     * @throws RuntimeException
     */
    <E extends BaseModel> void deleteAllInBatch(Class<E> clazz);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#getOne(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <E>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return ссылка на объект с данным идентификатором.
     */
    <E extends BaseModel<ID>, ID extends Serializable> E getOne(Class<E> clazz, ID id);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#saveAndFlush(Object)
     * @param clazz  класс бизнес-модели
     * @param entity коллекция сущностей
     * @param <E>    тип бизнес-модели
     * @return сущность, которая была сохранена
     */
    <E extends BaseModel<ID>, ID extends Serializable> E saveAndFlush(Class<E> clazz, E entity);

    /**
     * @see CrudRepository#save(Iterable)
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     * @param clazz    класс бизнес-модели
     * @param entities коллекция сущностей {@link Iterable}
     * @param <E>      тип бизнес-модели
     * @return список сущностей
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> saveAndFlush(Class<E> clazz, List<E> entities);

    /**
     * @see CrudRepository#save(Iterable)
     * @param clazz  класс бизнес-модели
     * @param entities коллекция сущностей {@link Iterable}
     * @param <E>    тип бизнес-модели
     * @return сохраненные бизнес-модели
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> save(Class<E> clazz, List<E> entities);

    /**
     * @see QueryDslPredicateExecutor#findOne(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>    тип бизнес-модели
     * @return сущность
     */
    <E extends BaseModel<ID>, ID extends Serializable> E findOne(Class<E> clazz, Predicate predicate);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>    тип бизнес-модели
     * @return найденные бизнес-модели
     */
    <E extends BaseModel<ID>, ID extends Serializable> List<E> findAll(Class<E> clazz, Predicate predicate);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate, Sort)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param sort заданная сортировка {@link Sort}
     * @param <E>    тип бизнес-модели
     * @return найденные бизнес-модели
     */
    <E extends BaseModel> List<E> findAll(Class<E> clazz, Predicate predicate, Sort sort);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate, Pageable)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param pageable параметры страницы {@link Pageable}
     * @param <E>    тип бизнес-модели
     * @return страница, содержащая бизнес-модели
     */
    <E extends BaseModel> Page<E> findAll(Class<E> clazz, Predicate predicate, Pageable pageable);

    /**
     * @see QueryDslPredicateExecutor#count(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>    тип бизнес-модели
     * @return количество сущностей, удолетворяющих предикату
     */
    <E extends BaseModel> long count(Class<E> clazz, Predicate predicate);

    /**
     * @see QueryDslPredicateExecutor#exists(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <E>    тип бизнес-модели
     * @return true, если существуют бизнес-модели, удолетворяющие предикату
     */
    <E extends BaseModel> boolean exists(Class<E> clazz, Predicate predicate);

}
       
    

