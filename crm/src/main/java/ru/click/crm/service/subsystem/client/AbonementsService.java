package ru.click.crm.service.subsystem.client;


import ru.click.crm.represent.page.schedule.SubscriptionApi;

import java.util.List;

/**
 * <p>
 * Создан 18.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface AbonementsService {

    /**
     * Возвращает список активных абонементов клиента
     *
     * @param clientId уникальный идентификатор клиента
     * @return список абонементов
     */
    List<SubscriptionApi> abonements(Long clientId);
}
