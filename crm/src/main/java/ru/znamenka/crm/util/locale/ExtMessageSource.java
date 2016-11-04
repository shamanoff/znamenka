package ru.znamenka.crm.util.locale;


import org.springframework.context.MessageSource;

/**
 * <p>
 *     Интерфейс расширяет стандартный {@link MessageSource}
 *     для более удобного использования
 * <p>
 * Создан 27.06.2016
 * <p>
 * Изменения:
 * <p>
 * 04.07.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *         Изменен метод {@link ExtMessageSource#getMessage(String, String)}.
 *         Теперь второй аргумент означает не сообщение по умолчанию, а код
 *         сообщения по умолчанию
 *     </li>
 * </ul>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface ExtMessageSource extends MessageSource {

    /**
     * Получаем сообщение по ее коду в messages.properties
     * @param messageCode ключ сообщения
     * @return текст сообщения
     */
    String getMessage(String messageCode);

    /**
     * Получаем сообщение по ее коду в messages.properties
     * @param messageCode ключ сообщения
     * @param defaultCode код сообщения по умолчанию, если messageCode не найден
     * @return текст сообщения
     */
    String getMessage(String messageCode, String defaultCode);

    /**
     * Получаем сообщение по ее коду в messages.properties, подставляя параметры
     * @see MessageSource#getMessage(String, Object[], Locale)
     * @param messageCode ключ сообщения
     * @param args параметры
     * @return текст сообщения
     */
    String getMessage(String messageCode, Object[] args);
}
