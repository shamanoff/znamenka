package ru.znamenka.crm.service.subsystem.client;


import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import ru.znamenka.crm.represent.domain.ClientApi;
import ru.znamenka.jpa.represent.impl.BaseExecutor;
import ru.znamenka.jpa.model.Client;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.ZERO;
import static ru.znamenka.jpa.model.QClient.client;
import static ru.znamenka.jpa.model.QClientAbonement.clientAbonement;
import static ru.znamenka.jpa.model.QTrainer.trainer;
import static ru.znamenka.jpa.model.QTraining.training;

/**
 * Создан 03.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class ClientExecutor extends BaseExecutor<Client, ClientApi> implements ClientsQueryService {

    /**
     * {@inheritDoc}
     *
     * @return список клиентов
     */
    @Override
    public List<ClientApi> activeClients() {
        JPAQuery<Client> query = getQuery();
        query
                .select(client).distinct()
                .from(client)
                .innerJoin(client.abonements, clientAbonement)
                .where(clientAbonement.trainingCount.gt(ZERO));

        List<Client> clients = query.fetch();
        return toApi(clients);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientApi> clientsByTrainerId(Long trainerId) {
        JPAQuery<Client> query = getQuery();
        query.select(client).distinct()
                .from(client)
                .innerJoin(client.trainings, training)
                .innerJoin(training.trainer, trainer)
                .where(trainer.id.eq(trainerId));

        List<Client> clients = query.fetch();
        return toApi(clients);
    }
}
