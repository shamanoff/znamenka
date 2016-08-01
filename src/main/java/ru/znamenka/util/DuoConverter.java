package ru.znamenka.util;

import org.springframework.core.convert.converter.Converter;

/**
 * <p>
 *     Интерфейс конвертера для конвертации объекта типа E в объект типа А
 * <p>
 * Создан 10.06.2016
 * <p>
 * Изменения:
 * <p>
 * 16.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *         Сократил функционал, убрав лишние метод. Теперь в интерфейсе
 *          определен метод для обратной конвертации.
 *     </li>
 *     <li>Перенес класс в другой пакет, так как никакой семантики теперь не несет</li>
 * </ul>
 * @see Converter
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface DuoConverter<E, A> extends Converter<E, A> {

    /**
     * Конвертирует в обратную сторону
     * {@inheritDoc}
     * @param source
     * @return
     */
    E convertTo(A source);
}
