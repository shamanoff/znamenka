package ru.znamenka.api.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.ClientApi;
import ru.znamenka.jpa.model.Client;

@Component
public class ClientApiConverter implements ApiConverter<Client, ClientApi> {

    @Override
    public Class<ClientApi> getApiType() {
        return ClientApi.class;
    }

    @Override
    public Class<Client> getEntityType() {
        return Client.class;
    }

    @Override
    public Client convertTo(ClientApi source) {
        Client client = new Client();
        client.setId(source.getId());
        client.setBirthDate(source.getBirthDate());
        client.setEmail(source.getEmail());
        client.setName(source.getFname());
        client.setSurname(source.getSname());
        client.setPhone(source.getPhone());
        client.setComment(source.getComment());
        client.setMale(source.getMale());
        return client;
    }

    @Override
    public ClientApi convert(Client client) {
        ClientApi api = new ClientApi();
        api.setId(client.getId());
        api.setName(client.getName() + " " + client.getSurname());
        api.setFname(client.getName());
        api.setSname(client.getSurname());
        api.setEmail(client.getEmail());
        api.setBirthDate(client.getBirthDate());
        api.setMale(client.getMale());
        api.setComment(client.getComment());
        api.setPhone(client.getPhone());
        return api;
    }


}
