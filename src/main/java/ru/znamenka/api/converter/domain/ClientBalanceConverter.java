package ru.znamenka.api.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.ClientBalance;
import ru.znamenka.jpa.model.Client;

import static ru.znamenka.util.Utils.join;

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
public class ClientBalanceConverter implements ApiConverter<Client, ClientBalance> {

    @Override
    public Class<Client> getEntityType() {
        return Client.class;
    }

    @Override
    public Client convertTo(ClientBalance source) {
        Client client = new Client();
        client.setId(source.getId());
        client.setName(join(source.getFname(), source.getLname()));
        return client;
    }

    /**
     * todo рассмотреть различные кейсы насчет имени и баланса
     * @param client
     * @return
     */
    @Override
    public ClientBalance convert(Client client) {
        ClientBalance clientB = new ClientBalance();
        String[] name = client.getName().split(" ");
        clientB.setFname(name[0]);
        clientB.setLname(name[1]);
        return clientB;
    }

    @Override
    public Class<ClientBalance> getApiType() {
        return ClientBalance.class;
    }
}
