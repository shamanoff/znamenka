package ru.znamenka.service.page.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.service.ApiStore;

import java.util.List;

/**
 * Created by Сережа on 31.08.2016.
 */

@Service
@Slf4j
public class ClientPageService {

    @Autowired
    private EntityRepository repo;

    @Autowired
    @Qualifier("dataService")
    private ApiStore service;

    public List<ClientApi> getClients(){
        List<ClientApi> clientApiList = service.findAll(ClientApi.class);
        return clientApiList;

    }


}
