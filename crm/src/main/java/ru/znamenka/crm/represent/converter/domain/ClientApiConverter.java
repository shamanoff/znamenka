package ru.znamenka.crm.represent.converter.domain;


import org.springframework.stereotype.Component;
import ru.znamenka.crm.represent.converter.UpdatableApiConverter;
import ru.znamenka.crm.represent.domain.ClientApi;
import ru.znamenka.jpa.model.Client;

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
        client.setCarNumber(source.getCarNumber() == null ? null : source.getCarNumber().toUpperCase());
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
        if(source.getCarNumber() != null){
            entity.setCarNumber(source.getCarNumber() == null ? null : source.getCarNumber().toUpperCase());
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
        api.setCarNumber(client.getCarNumber());
        return api;
    }


}
