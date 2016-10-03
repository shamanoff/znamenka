package ru.znamenka.service.subsystem.client;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.page.client.ClientPurchaseApi;
import ru.znamenka.represent.page.schedule.SubscriptionApi;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.Executor;

import java.util.List;

import static org.springframework.util.Assert.notNull;
import static ru.znamenka.jpa.model.QClient.client;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;
import static ru.znamenka.jpa.model.QTrainer.trainer;
import static ru.znamenka.jpa.model.QTraining.training;

/**
 * Создан 29.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class ClientService implements IClientService {

    /**
     * Уникальные идентификаторы абонементов в таблице продуктов
     */
    private static final Number[] SUBSCRIPTIONS = new Long[]{1L, 2L, 3L};

    private final ApiStore service;

    private final Executor<Client, ClientApi> clientExecutor;

    private final Executor<Tuple, SubscriptionApi> subscriptionExecutor;

    @Autowired
    public ClientService(
            @Qualifier("dataService") ApiStore service,
            Executor<Client, ClientApi> clientExecutor,
            Executor<Tuple, SubscriptionApi> subscriptionExecutor
    ) {
        notNull(service);
        notNull(clientExecutor);
        notNull(subscriptionExecutor);
        this.service = service;
        this.clientExecutor = clientExecutor;
        this.subscriptionExecutor = subscriptionExecutor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiStore store() {
        return service;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientApi> allClients() {
        return service.findAll(ClientApi.class);
    }

    /**
     * {@inheritDoc}
     * <p>
     * SELECT c.*
     * FROM "таблица-клиентов" c
     * WHERE EXISTS(SELECT * FROM "таблица-покупок" p WHERE c.client_id = p.client_id AND p.expired = false)
     *
     * @return список клиентов
     */
    @Override
    public List<ClientApi> activeClients() {
        JPAQuery<Client> query = clientExecutor.getQuery();
        query
                .from(client)
                .where(
                        JPAExpressions
                                .select(purchase)
                                .from(purchase)
                                .where(purchase.clientId.eq(client.id).and(purchase.expired.eq(false))).exists()
                );

        List<Client> clients = query.fetch();
        return clientExecutor.toApi(clients);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientApi> clientsByTrainerId(Long trainerId) {
        JPAQuery<Client> query = clientExecutor.getQuery();
        query.select(client).distinct()
                .from(client)
                .innerJoin(client.trainings, training)
                .innerJoin(training.trainer, trainer)
                .where(trainer.id.eq(trainerId));

        List<Client> clients = query.fetch();
        return clientExecutor.toApi(clients);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientApi update(ClientApi upClient) {
        ClientApi clientApi = service.findOne(ClientApi.class, upClient.getId());
        clientApi.setFname(upClient.getFname());
        clientApi.setSname(upClient.getSname());
        clientApi.setBirthDate(upClient.getBirthDate());
        clientApi.setPhone(upClient.getPhone());
        clientApi.setEmail(upClient.getEmail());
        clientApi.setComment(upClient.getComment());
        clientApi.setMale(upClient.getMale());
        return service.update(ClientApi.class, clientApi);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TrainingApi> trainings(Long clientId) {
        return service.findAll(TrainingApi.class, training.clientId.eq(clientId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientPurchaseApi> purchases(Long clientId) {
        return service.findAll(ClientPurchaseApi.class, purchase.client.id.eq(clientId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientApi clientByPhone(String phone) {
        return service.findOne(ClientApi.class, client.phone.eq(phone));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubscriptionApi> subscriptions(Long clientId) {
        List<Tuple> tuple = initSubScrQuery(clientId).fetch();
        return subscriptionExecutor.toApi(tuple);
    }

    /**
     * Инициализирует SQL запрос
     * SELECT pur.id, pr.product_name
     * FROM purchase pur
     *   LEFT JOIN product pr ON pur.product_id = pr.id
     * WHERE pur.client_id = ?
     *   AND pr.id in (?)
     *   AND pr.expire_days > 0
     * @param clientId уникальный идентификатор клиента
     * @return запрос
     */
    private JPAQuery<Tuple> initSubScrQuery(Long clientId) {
        return subscriptionExecutor.getQuery()
                .select(purchase.id, product.productName)
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(purchase.client.id.eq(clientId)
                        .and(product.id.in(SUBSCRIPTIONS))
                        .and(product.expireDays.gt(0)));
    }

}
