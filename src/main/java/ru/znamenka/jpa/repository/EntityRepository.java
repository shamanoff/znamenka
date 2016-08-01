package ru.znamenka.jpa.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

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
     * @param <T>    тип бизнес-модели
     * @return сущность, которая была сохранена
     */
    <T> T save(Class<T> clazz, T entity);

    /**
     * @see CrudRepository#findOne(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <T>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return объект бизнес-модели с заданным id
     */
    <T, ID extends Serializable> T findOne(Class<T> clazz, ID id);

    /**
     * @see CrudRepository#exists(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <T>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return true, если хранится, иначе false
     */
    <T, ID extends Serializable> boolean exists(Class<T> clazz, ID id);

    /**
     * @see CrudRepository#findAll()
     * @param clazz класс бизнес-модели
     * @param <T>   тип бизнес-модели
     * @return список сущностей
     */
    <T> List<T> findAll(Class<T> clazz);
    /**
     * @see PagingAndSortingRepository#findAll(Sort)
     * @param clazz класс бизнес-модели
     * @param <T>   тип бизнес-модели
     * @param sort сортировка {@link Sort}
     * @return список сущностей
     * @throws RuntimeException
     */
    <T> List<T> findAll(Class<T> clazz, Sort sort);

    /**
     * Обновляет запись в базе
     * @param clazz класс бизнес-модели
     * @param entity обновленная бизнес модель
     * @param <T> тип бизнес-модели
     * @return обновленная бизнес-модель
     */
    <T>  T update(Class<T> clazz, T entity);
    /**
     * @see PagingAndSortingRepository#findAll(Pageable)
     * @see Page
     * @param clazz    класс бизнес-модели
     * @param <T>      тип бизнес-модели
     * @param pageable объект, содержащий информацию о номере странице и количестве записей на этой странице
     * @return станица, которая содержит данные и служебную информацию
     */
    <T> Page<T> findAll(Class<T> clazz, Pageable pageable);
    /**
     * @see CrudRepository#count()
     * @param clazz класс бизнес-модели
     * @param <T>   тип бизнес-модели
     * @return  количество сущностей
     */
    <T> long count(Class<T> clazz);

    /**
     * @see CrudRepository#delete(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <T>   тип бизнес-модели
     * @param <ID>  тип идентификатора
     */
    <T, ID extends Serializable> void delete(Class<T> clazz, ID id);

    /**
     * @see CrudRepository#deleteAll()
     * @param clazz класс бизнес-модели
     * @param <T>   тип бизнес-модели
     */
    <T> void deleteAll(Class<T> clazz);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     */
    void flush();

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#deleteInBatch(Iterable)
     * @param clazz класс бизнес-модели
     * @param <T>   тип бизнес-модели
     * @throws RuntimeException
     */
    <T> void deleteAllInBatch(Class<T> clazz);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#getOne(java.io.Serializable)
     * @param clazz класс бизнес-модели
     * @param id    уникальный идентификатор бизнес-модели
     * @param <T>   тип бизнес-модели
     * @param <ID>  тип уникального идентификатора бизнес-модели
     * @return ссылка на объект с данным идентификатором.
     */
    <T, ID extends Serializable> T getOne(Class<T> clazz, ID id);

    /**
     * @see org.springframework.data.jpa.repository.JpaRepository#saveAndFlush(Object)
     * @param clazz  класс бизнес-модели
     * @param entity коллекция сущностей
     * @param <T>    тип бизнес-модели
     * @return сущность, которая была сохранена
     */
    <T> T saveAndFlush(Class<T> clazz, T entity);

    /**
     * @see CrudRepository#save(Iterable)
     * @see org.springframework.data.jpa.repository.JpaRepository#flush()
     * @param clazz    класс бизнес-модели
     * @param entities коллекция сущностей {@link Iterable}
     * @param <T>      тип бизнес-модели
     * @return список сущностей
     */
    <T> List<T> saveAndFlush(Class<T> clazz, List<T> entities);

    /**
     * @see CrudRepository#save(Iterable)
     * @param clazz  класс бизнес-модели
     * @param entities коллекция сущностей {@link Iterable}
     * @param <T>    тип бизнес-модели
     * @return сохраненные бизнес-модели
     */
    <T> List<T> save(Class<T> clazz, List<T> entities);

    /**
     * @see QueryDslPredicateExecutor#findOne(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <T>    тип бизнес-модели
     * @return сущность
     */
    <T> T findOne(Class<T> clazz, Predicate predicate);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <T>    тип бизнес-модели
     * @return найденные бизнес-модели
     */
    <T> List<T> findAll(Class<T> clazz, Predicate predicate);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate, Sort)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param sort заданная сортировка {@link Sort}
     * @param <T>    тип бизнес-модели
     * @return найденные бизнес-модели
     */
    <T> List<T> findAll(Class<T> clazz, Predicate predicate, Sort sort);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate, Pageable)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param pageable параметры страницы {@link Pageable}
     * @param <T>    тип бизнес-модели
     * @return страница, содержащая бизнес-модели
     */
    <T> Page<T> findAll(Class<T> clazz, Predicate predicate, Pageable pageable);

    /**
     * @see QueryDslPredicateExecutor#count(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <T>    тип бизнес-модели
     * @return количество сущностей, удолетворяющих предикату
     */
    <T> long count(Class<T> clazz, Predicate predicate);

    /**
     * @see QueryDslPredicateExecutor#exists(Predicate)
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param <T>    тип бизнес-модели
     * @return true, если существуют бизнес-модели, удолетворяющие предикату
     */
    <T> boolean exists(Class<T> clazz, Predicate predicate);
    /**
     * @see QueryDslPredicateExecutor#findAll(OrderSpecifier[])
     * @param clazz  класс бизнес-модели
     * @param orders сортировка {@link OrderSpecifier}
     * @param <T>    тип бизнес-модели
     * @return возвращает бизнес-модели в заданном порядке
     */
    <T> List<T> findAll(Class<T> clazz, OrderSpecifier[] orders);

    /**
     * @see QueryDslPredicateExecutor#findAll(Predicate, OrderSpecifier[])
     * @param clazz  класс бизнес-модели
     * @param predicate предикат {@link Predicate}
     * @param orders сортировка {@link OrderSpecifier}
     * @param <T>    тип бизнес-модели
     * @return возвращает бизнес-модели, соответствующие предикату в заданном порядке
     */
    <T> List<T> findAll(Class<T> clazz, Predicate predicate, OrderSpecifier[] orders);


}
       
    

