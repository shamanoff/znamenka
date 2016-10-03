package ru.znamenka.service;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.BaseModel;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.repository.FacadeDomainRepository;
import ru.znamenka.represent.DomainApi;
import ru.znamenka.represent.UpdatableApi;
import ru.znamenka.represent.converter.ApiConverter;
import ru.znamenka.represent.converter.UpdatableApiConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * Класс, представляющий собой декоратор к классу
 * {@link FacadeDomainRepository}, добавляющий
 * конвертацию сущностей ({@link BaseModel} в их представления ({@link DomainApi}),
 * а так же для оборачивания исключения в свои собственные
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service("convertService")
@Slf4j
@SuppressWarnings("unchecked")
public class ConvertEntityService implements ApiStore {

    /**
     * Декорируемый класс
     *
     * @see FacadeDomainRepository
     */
    private final EntityRepository facade;

    /**
     * Корзинка с конвертерами
     */
    private final Map<Class, ApiConverter> converterBucket;

    /**
     * Корзинка с конвертерами
     */
    private final Map<Class, UpdatableApiConverter> upConverterBucket;

    /**
     * Создает новый конверт сервис на основе декорируемого класс и хранилища бинов-конвертеров
     *  @param facade          декорируемый класс
     * @param converterBucket хранилище бинов-конвертеров
     * @param upConverterBucket
     */
    @Autowired
    public ConvertEntityService(
            EntityRepository facade,
            Map<Class, ApiConverter> converterBucket,
            Map<Class, UpdatableApiConverter> upConverterBucket
    ) {
        notEmpty(converterBucket);
        notEmpty(upConverterBucket);
        notNull(facade);
        this.facade = facade;
        this.converterBucket = converterBucket;
        this.upConverterBucket = upConverterBucket;
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> List<A> findAll(Class<A> clazz) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass));

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Sort)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> List<A> findAll(Class<A> clazz, Sort sort) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        List<E> list = facade.findAll(eClass, sort);
        return convertListToApi(converter, list);
    }


    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Pageable)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> Page<A> findAll(Class<A> clazz, Pageable pageable) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        Page<E> page = facade.findAll(eClass, pageable);
        return page.map(converter);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#count(Class)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> long count(Class<A> clazz) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return facade.count(eClass);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#delete(Class, Serializable)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> void delete(Class<A> clazz, ID id) {

        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        facade.delete(eClass, id);

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#deleteAll(Class)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> void deleteAll(Class<A> clazz) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        facade.deleteAll(eClass);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#flush()
     */
    @Override
    public void flush() throws RuntimeException {
        facade.flush();
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#deleteAllInBatch(Class)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> void deleteAllInBatch(Class<A> clazz) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();

        facade.deleteAllInBatch(eClass);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#getOne(Class, Serializable)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> A getOne(Class<A> clazz, ID id) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return converter.convert(facade.getOne(eClass, id));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> A saveAndFlush(Class<A> clazz, A api) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        E entity = converter.convertTo(api);
        E saved = facade.saveAndFlush(eClass, entity);
        return converter.convert(saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#saveAndFlush(Class, List)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> List<A> saveAndFlush(Class<A> clazz, List<A> apies) {
        notNull(clazz);
        if (apies.isEmpty()) return apies;
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        List<E> entities = convertListToEntity(converter, apies);
        List<E> saved = facade.saveAndFlush(eClass, entities);
        return convertListToApi(converter, saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#save(Class, List)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> List<A> save(Class<A> clazz, List<A> apies) throws RuntimeException {
        notNull(clazz);
        if (apies.isEmpty()) return apies;
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        List<E> entities = convertListToEntity(converter, apies);
        List<E> saved = facade.save(eClass, entities);
        return convertListToApi(converter, saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> A save(Class<A> clazz, A api) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        E entity = converter.convertTo(api);
        entity = facade.save(eClass, entity);
        return converter.convert(entity);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findOne(Class, Serializable)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> A findOne(Class<A> clazz, ID id) {
        notNull(clazz);
        ApiConverter<BaseModel, A> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        BaseModel entity = facade.findOne(eClass, id);
        return converter.convert(entity);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findOne(Class, Predicate)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> A findOne(Class<A> clazz, Predicate predicate) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return converter.convert(facade.findOne(eClass, predicate));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> List<A> findAll(Class<A> clazz, Predicate predicate) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass, predicate));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate, Sort)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> List<A> findAll(Class<A> clazz, Predicate predicate, Sort sort) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass, predicate, sort));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate, Pageable)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> Page<A> findAll(Class<A> clazz, Predicate predicate, Pageable pageable) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        Page<E> page = facade.findAll(eClass, predicate, pageable);
        return page.map(converter);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#count(Class, Predicate)
     */
    @Override
    public <E extends BaseModel, A extends DomainApi> long count(Class<A> clazz, Predicate predicate) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return facade.count(eClass, predicate);

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#exists(Class, Predicate)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> boolean exists(Class<A> clazz, Predicate predicate) throws RuntimeException {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return facade.exists(eClass, predicate);
    }

    @Override
    public <E extends BaseModel<ID>, A extends DomainApi & UpdatableApi<ID>, ID extends Serializable> A update(Class<A> clazz, A api) {
        notNull(clazz);
        UpdatableApiConverter<E, A, ID> converter = upConverterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        E entityDb = facade.findOne(eClass, api.getId());
        E entity = converter.convertTo(api, entityDb);
        return converter.convert(facade.save(eClass, entity));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#exists(Class, Serializable)
     */
    @Override
    public <E extends BaseModel<ID>, A extends DomainApi, ID extends Serializable> boolean exists(Class<A> clazz, ID id) {
        notNull(clazz);
        ApiConverter<E, A> converter = converterBucket.get(clazz);
        Class<E> eClass = converter.getEntityType();
        return facade.exists(eClass, id);
    }

    /**
     * Метод для конвертации списка моделей {@link BaseModel} в список представлений {@link DomainApi}
     *
     * @param converter реализация конвертера
     * @param list      список моделей
     * @param <E>       тип модели
     * @param <A>       тип представления
     * @return список представлений
     */
    private <E extends BaseModel, A extends DomainApi> List<A> convertListToApi(ApiConverter<E, A> converter, List<E> list) {
        notNull(converter);
        notNull(list);
        return list.stream().map(converter::convert).collect(toList());
    }

    /**
     * Метод для конвертации списка представлений {@link DomainApi} в список моделей {@link BaseModel}
     *
     * @param converter реализация конвертера
     * @param list      список представлений
     * @param <E>       тип модели
     * @param <A>       тип представления
     * @return список моделей
     */
    private <E extends BaseModel, A extends DomainApi> List<E> convertListToEntity(ApiConverter<E, A> converter, List<A> list) {
        notNull(converter);
        notNull(list);
        return list.stream().map(converter::convertTo).collect(toList());
    }

}
