package ru.znamenka.api.converter;

/**
 * <p>
 *     Интерфейс, который возвращает тип представления.
 * <p>
 * Создан 04.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface ApiInfo<A> {

    /**
     * Возвращает тип представления
     * @return тип представления
     */
    Class<A> getApiType();
}
