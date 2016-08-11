package ru.znamenka.controller.rest.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.znamenka.api.BaseApi;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.service.validation.RequestBodyValidator;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * <p>
 * Абстрактный класс, реализирующий интерфейс {@link CRUDController}
 * <p>
 * Создан 20.06.2016
 * <p>
 * Изменения:
 * <p>
 * 01.07.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Добавил exception handler для обработки ошибки с constraint'ами базы</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public abstract class ARestController<T extends BaseApi, ID extends Serializable>
        extends RequestBodyValidator<T>
        implements CRUDController<T, ID> {

    /**
     * Прокси
     * Служит для обеспечения доступа к данным.
     */
    @Autowired
    @Qualifier("dataService")
    private EntityRepository service;

    /**
     * {@inheritDoc}
     * @param pageable
     * @return
     */
    @Override
    @RequestMapping(value = "/pages", method = GET, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<Page<T>> getApiList(Pageable pageable) {
        Page<T> apies = service.findAll(getApiClass(), pageable);
        return ResponseEntity.ok(apies);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    @RequestMapping(value = "/all", method = GET, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<List<T>> getApiList() {
        List<T> apies = service.findAll(getApiClass());
        if (apies.isEmpty()) return ResponseEntity.status(NO_CONTENT).body(apies);
        return ResponseEntity.ok(apies);
    }

    /**
     * {@inheritDoc}
     * @param id уникальный идентификатор представления
     * @return
     */
    @RequestMapping(value = "/{id}", method = GET, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<T> getApiById(@PathVariable("id") ID id) {
        T entity = service.findOne(getApiClass(), id);
        return ResponseEntity.ok(entity);
    }

    /**
     * {@inheritDoc}
     * @param id уникальный идентификатор представления
     * @return
     */
    @Override
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") ID id) {
        service.delete(getApiClass(), id);
        return ResponseEntity.ok().build();
    }

    /**
     * {@inheritDoc}
     * @param api представление
     * @param bindingResult результат валидации
     * @return
     */
    @Override
    @RequestMapping(value = "/", method = POST,
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity save(@Valid @RequestBody T api, BindingResult bindingResult) {
        prepare(api, bindingResult);
        return ResponseEntity.status(CREATED).body(service.save(getApiClass(), api));
    }

    /**
     * {@inheritDoc}
     * @param api обновленное состояние представления
     * @return
     */
    @Override
    @RequestMapping(value = "/", method = PUT,
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity update(@Valid @RequestBody T api, BindingResult bindingResult) {
        prepare(api, bindingResult);
        //// TODO: 11.08.2016  
        return ResponseEntity.ok(api);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public abstract Class<T> getApiClass();

}
