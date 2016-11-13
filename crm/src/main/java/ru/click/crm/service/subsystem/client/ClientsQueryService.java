package ru.click.crm.service.subsystem.client;


import ru.click.crm.represent.domain.ClientApi;

import java.util.List;

/**
 * <p>
 * Создан 18.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
// TODO: 18.10.2016 переименовать
public interface ClientsQueryService {

    /**
     * Возвращает список клиентов, у которых есть
     * активный абонемент
     *
     * @return список клиентов
     */
    List<ClientApi> activeClients();

    /**
     * Поиск клиентов по id тренера. Необходим для того,
     * чтобы тренер имел доступ только к своим клиентам
     *
     * @param trainerId уникальный идентификатор тренера
     * @return список клиентов
     */
    List<ClientApi> clientsByTrainerId(Long trainerId);
}
