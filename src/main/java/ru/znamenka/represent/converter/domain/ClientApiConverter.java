package ru.znamenka.represent.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.represent.converter.UpdatableApiConverter;
import ru.znamenka.represent.domain.ClientApi;

@Component
public class ClientApiConverter implements UpdatableApiConverter<Client, ClientApi, Long> {

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
    public Client convertTo(ClientApi source, Client entity) {
        if (source.getFname() != null) {
            entity.setName(source.getFname());
        }
        if (source.getSname() != null) {
            entity.setSurname(source.getSname());
        }
        if (source.getPhone() != null) {
            entity.setPhone(source.getPhone());
        }
        if (source.getEmail() != null) {
            entity.setEmail(source.getEmail());
        }
        return entity;
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
