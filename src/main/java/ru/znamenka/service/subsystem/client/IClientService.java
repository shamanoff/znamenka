package ru.znamenka.service.subsystem.client;

import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.page.client.ClientPurchaseApi;
import ru.znamenka.represent.page.schedule.SubscriptionApi;
import ru.znamenka.service.ApiStore;

import java.util.List;

/**
 * Создан 29.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface IClientService {

    /**
     * Позволяет обращаться к хранилищу данных для простых методов,
     * таких как findOne(id), save(api), etc
     *
     * @return ссылку на хранилище данных
     */
    ApiStore store();

    /**
     * Возвращает всех клиентов
     *
     * @return список клиентов
     */
    List<ClientApi> allClients();

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

    /**
     * Обновляет информацию о клиенте
     *
     * @param upClient обновленный клиент
     * @return обновленный клиент
     */
    ClientApi update(ClientApi upClient);

    /**
     * Поиск тренировок клиента
     *
     * @param clientId уникальный идентификатор клиента
     * @return список тренировок
     */
    List<TrainingApi> trainings(Long clientId);

    /**
     * Поиск покупок клиента
     *
     * @param clientId уникальный идентификатор клиента
     * @return список покупок
     */
    List<ClientPurchaseApi> purchases(Long clientId);

    /**
     * Поиск клиента по номеру телефона
     *
     * @param phone номер телефона
     * @return клиент
     */
    ClientApi clientByPhone(String phone);

    /**
     * Возвращает список активных абонементов клиента
     *
     * @param clientId уникальный идентификатор клиента
     * @return список абонементов
     */
    List<SubscriptionApi> subscriptions(Long clientId);


}
