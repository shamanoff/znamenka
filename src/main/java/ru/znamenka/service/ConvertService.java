package ru.znamenka.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.znamenka.api.BaseApi;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.jpa.model.BaseModel;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.repository.FacadeDomainRepository;

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
 * конвертацию сущностей ({@link BaseModel} в их представления ({@link BaseApi}),
 * а так же для оборачивания исключения в свои собственные
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service("convertService")
@Slf4j
@SuppressWarnings("unchecked")
public class ConvertService implements EntityRepository {

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
     * Контейнер бинов
     */
    @Autowired
    private BeanFactory ctx;

    /**
     * Создает новый конверт сервис на основе декорируемого класс и хранилища бинов-конвертеров
     *
     * @param facade          декорируемый класс
     * @param converterBucket хранилище бинов-конвертеров
     */
    @Autowired
    public ConvertService(
            @Qualifier("facadeRepository") EntityRepository facade
            , @Qualifier("convertersBucket") Map<Class, ApiConverter> converterBucket
    ) {
        notEmpty(converterBucket);
        this.facade = facade;
        this.converterBucket = converterBucket;
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class)
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass));

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Sort)
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Sort sort) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        List<BaseModel> list = facade.findAll(eClass, sort);
        return convertListToApi(converter, list);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#update(Class, Object)
     */
    @Override
    public <T> T update(Class<T> clazz, T api) {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        BaseModel entity = converter.convertTo(api);
        BaseModel updated = facade.update(eClass, entity);
        return converter.convert(updated);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Pageable)
     */
    @Override
    public <T> Page<T> findAll(Class<T> clazz, Pageable pageable) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        Page<BaseModel> page = facade.findAll(eClass, pageable);
        return page.map(converter);

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#count(Class)
     */
    @Override
    public <T> long count(Class<T> clazz) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return facade.count(eClass);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#delete(Class, Serializable)
     */
    @Override
    public <T, ID extends Serializable> void delete(Class<T> clazz, ID id) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        facade.delete(eClass, id);

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#deleteAll(Class)
     */
    @Override
    public <T> void deleteAll(Class<T> clazz) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
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
    public <T> void deleteAllInBatch(Class<T> clazz) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();

        facade.deleteAllInBatch(eClass);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#getOne(Class, Serializable)
     */
    @Override
    public <T, ID extends Serializable> T getOne(Class<T> clazz, ID id) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return converter.convert(facade.getOne(eClass, id));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#saveAndFlush(Class, Object)
     */
    @Override
    public <T> T saveAndFlush(Class<T> clazz, T api) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        BaseModel entity = converter.convertTo(api);

        BaseModel saved = facade.saveAndFlush(eClass, entity);
        return converter.convert(saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#saveAndFlush(Class, List)
     */
    @Override
    public <T> List<T> saveAndFlush(Class<T> clazz, List<T> apies) {
        notNull(clazz);
        if (apies.isEmpty()) return apies;
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        List<BaseModel> entities = convertListToEntity(converter, apies);
        List<BaseModel> saved = facade.saveAndFlush(eClass, entities);
        return convertListToApi(converter, saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#save(Class, List)
     */
    @Override
    public <T> List<T> save(Class<T> clazz, List<T> apies) throws RuntimeException {
        notNull(clazz);
        if (apies.isEmpty()) return apies;
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        List<BaseModel> entities = convertListToEntity(converter, apies);
        List<BaseModel> saved = facade.save(eClass, entities);
        return convertListToApi(converter, saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#save(Class, Object)
     */
    @Override
    public <T> T save(Class<T> clazz, T api) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        BaseModel entity = converter.convertTo(api);
        BaseModel saved = facade.save(eClass, entity);
        return converter.convert(saved);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findOne(Class, Serializable)
     */
    @Override
    public <T, ID extends Serializable> T findOne(Class<T> clazz, ID id) {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
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
    public <T> T findOne(Class<T> clazz, Predicate predicate) {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return converter.convert(facade.findOne(eClass, predicate));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate)
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Predicate predicate) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass, predicate));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate, Sort)
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Predicate predicate, Sort sort) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass, predicate, sort));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate, Pageable)
     */
    @Override
    public <T> Page<T> findAll(Class<T> clazz, Predicate predicate, Pageable pageable) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        Page<BaseModel> page = facade.findAll(eClass, predicate, pageable);
        return page.map(converter);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#count(Class, Predicate)
     */
    @Override
    public <T> long count(Class<T> clazz, Predicate predicate) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return facade.count(eClass, predicate);

    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#exists(Class, Predicate)
     */
    @Override
    public <T> boolean exists(Class<T> clazz, Predicate predicate) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return facade.exists(eClass, predicate);
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, OrderSpecifier[])
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, OrderSpecifier[] orders) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass, orders));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#findAll(Class, Predicate, OrderSpecifier[])
     */
    @Override
    public <T> List<T> findAll(Class<T> clazz, Predicate predicate, OrderSpecifier[] orders) throws RuntimeException {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return convertListToApi(converter, facade.findAll(eClass, predicate, orders));
    }

    /**
     * Декорирует метод фасада репозиториев
     *
     * @see EntityRepository#exists(Class, Serializable)
     */
    @Override
    public <T, ID extends Serializable> boolean exists(Class<T> clazz, ID id) {
        notNull(clazz);
        ApiConverter<BaseModel, T> converter = converterBucket.get(clazz);
        Class<BaseModel> eClass = converter.getEntityType();
        return facade.exists(eClass, id);
    }

    /**
     * Метод для конвертации списка моделей {@link BaseModel} в список представлений {@link BaseApi}
     *
     * @param converter реализация конвертера
     * @param list      список моделей
     * @param <E>       тип модели
     * @param <T>       тип представления
     * @return список представлений
     */
    private <E extends BaseModel, T> List<T> convertListToApi(ApiConverter<E, T> converter, List<E> list) {
        notNull(converter);
        notNull(list);
        return list.stream().map(converter::convert).collect(toList());
    }

    /**
     * Метод для конвертации списка представлений {@link BaseApi} в список моделей {@link BaseModel}
     *
     * @param converter реализация конвертера
     * @param list      список представлений
     * @param <E>       тип модели
     * @param <T>       тип представления
     * @return список моделей
     */
    private <E extends BaseModel, T> List<E> convertListToEntity(ApiConverter<E, T> converter, List<T> list) {
        notNull(converter);
        notNull(list);
        return list.stream().map(converter::convertTo).collect(toList());
    }

}
