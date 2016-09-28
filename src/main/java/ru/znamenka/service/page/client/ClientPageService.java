package ru.znamenka.service.page.client;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.page.BaseExecutor;

import java.util.List;

import static ru.znamenka.jpa.model.QClient.client;
import static ru.znamenka.jpa.model.QTrainer.trainer;
import static ru.znamenka.jpa.model.QTraining.training;

@Service
@Slf4j
public class ClientPageService extends BaseExecutor<Client, ClientApi> {

    @Autowired
    @Qualifier("dataService")
    private ApiStore service;

    public List<ClientApi> getClientsByTrainer(Long trainerId) {
        JPAQuery<Client> query = getQuery();
        query.select(client)
                .from(client)
                .innerJoin(client.trainings, training)
                .innerJoin(training.trainer, trainer)
                .where(trainer.id.eq(trainerId));

        List<Client> clients = query.fetch();
        return toApi(clients);
    }

    public ClientApi updateClient(ClientApi upClient, Long clientId) {
        ClientApi clientApi = service.findOne(ClientApi.class, clientId);
        clientApi.setFname(upClient.getFname());
        clientApi.setSname(upClient.getSname());
        clientApi.setBirthDate(upClient.getBirthDate());
        clientApi.setPhone(upClient.getPhone());
        clientApi.setEmail(upClient.getEmail());
        clientApi.setComment(upClient.getComment());
        clientApi.setMale(upClient.getMale());
        return service.update(ClientApi.class, clientApi);
    }


}
