package ru.znamenka.service.client;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.page.BaseExecutor;

import java.util.List;

import static ru.znamenka.jpa.model.QClient.client;
import static ru.znamenka.jpa.model.QPurchase.purchase;

/**
 * Создан 29.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class ClientService extends BaseExecutor<Client, ClientApi> {

    private final ApiStore service;

    @Autowired
    public ClientService(@Qualifier("dataService") ApiStore service) {
        this.service = service;
    }


    /**
     * Возвращает всех клиентов
     *
     * @return список клиентов
     */
    public List<ClientApi> allClients() {
        return service.findAll(ClientApi.class);
    }

    /**
     * Возвращает список клиентов, у которых есть
     * активный абонемент
     * <p>
     * SELECT c.*
     * FROM "таблица-клиентов" c
     * WHERE EXISTS(SELECT * FROM "таблица-покупок" p WHERE c.client_id = p.client_id AND p.expired = false)
     *
     * @return список клиентов
     */
    public List<ClientApi> allActiveClients() {
        JPAQuery<Client> query = getQuery();
        query
                .from(client)
                .where(
                        JPAExpressions
                                .select(purchase)
                                .from(purchase)
                                .where(purchase.clientId.eq(client.id).and(purchase.expired.eq(false))).exists()
                );

        List<Client> clients = query.fetch();
        return toApi(clients);
    }



}
