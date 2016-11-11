package ru.click.crm.service.subsystem.client;


import ru.click.crm.represent.page.client.CountOfTraining;

import java.util.List;

/**
 * <p>
 * Создан 18.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface AbonementsCounter {

    /**
     * Возвращает количество тренировок по всем купленным абонементам клиента
     * @param clientId уникальный идентификатор клиента
     * @return список количества тренировок абонементов клиента
     */
    List<CountOfTraining> countOfTraining(Long clientId);
}
