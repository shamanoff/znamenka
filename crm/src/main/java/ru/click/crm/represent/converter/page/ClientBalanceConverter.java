package ru.click.crm.represent.converter.page;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.click.crm.represent.page.ClientBalance;
import ru.click.core.represent.converter.ApiConverter;
import ru.click.core.model.Client;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * <p>
 * Создан 03.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
@Slf4j
public class ClientBalanceConverter implements ApiConverter<Client, ClientBalance> {


    @Override
    public Class<Client> getEntityType() {
        return Client.class;
    }

    @Override
    public Client convertTo(ClientBalance source) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param client
     * @return
     */
    @Override
    public ClientBalance convert(Client client) {
        notNull(client);
        ClientBalance cb = new ClientBalance();

        cb.setId(client.getId());
        cb.setName(client.getName());
        cb.setSurname(client.getSurname());
        cb.setEmail(client.getEmail());
        cb.setBirthDate(client.getBirthDate());
        cb.setPhone(client.getPhone());
        return cb;


    }

    @Override
    public Class<ClientBalance> getApiType() {
        return ClientBalance.class;
    }
}
