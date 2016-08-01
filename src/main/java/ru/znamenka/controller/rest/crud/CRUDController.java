package ru.znamenka.controller.rest.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.znamenka.api.BaseApi;

import java.io.Serializable;

/**
 * <p>
 *      Интефейс для реализации базовых CRUD операций
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface CRUDController<T extends BaseApi, ID extends Serializable> {

    /**
     * Отдает клиенту список представлений по странично
     * @param pageable запрос страницы
     * @return страницу с данными
     * @see Pageable
     * @see Page
     */
    ResponseEntity<Page<T>> getApiList(Pageable pageable);

    /**
     * Отдает клиенту список представлений
     * @return список представлений
     */
    ResponseEntity getApiList();

    /**
     * Отдает представление по id бизнес-модели
     * @param id уникальный идентификатор представления
     * @return представление
     */
    ResponseEntity<T> getApiById(ID id);

    /**
     * Удаляет бизнес-модель из базы по id
     * @param id уникальный идентификатор представления
     * @return статус 200, если операция прошла успешна
     */
    ResponseEntity delete(ID id);

    /**
     * Сохраняет представление
     * @param api представление
     * @param bindingResult результат валидации
     * @return статус 201, если операция прошла успешно
     */
    ResponseEntity save(T api, BindingResult bindingResult);

    /**
     * Обновляет данные
     * @param api обновленное состояние представления
     * @return статус 200, если операция прошла успешна
     */
    ResponseEntity update(T api, BindingResult bindingResult);

    /**
     * Определяет тип представления
     * @return тип представления
     */
    Class<T> getApiClass();
}
