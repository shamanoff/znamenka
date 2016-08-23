package ru.znamenka.api.converter.domain;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.ClientApi;
import ru.znamenka.jpa.model.Client;

import static java.util.Arrays.asList;

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
        String[] name = source.getName().split(" ");
        client.setName(source.getName());
        if (name.length == 2) {
            client.setSurname(name[2]);
        }
        client.setBirthDate(source.getBirthDate());
        client.setEmail(source.getEmail());
        client.setPhone(source.getPhone());

        return client;
    }

    @Override
    public ClientApi convert(Client client) {
        return ClientApi
                .builder()
                .id(client.getId())
                .name(StringUtils.join(asList(client.getName(),client.getSurname()) , " "))
                .birthDate(client.getBirthDate())
                .phone(client.getPhone())
                .email(client.getEmail())
                .build();
    }


}
