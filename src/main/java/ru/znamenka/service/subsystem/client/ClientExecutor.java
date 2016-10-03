package ru.znamenka.service.subsystem.client;

import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.service.BaseExecutor;

/**
 * Создан 03.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class ClientExecutor extends BaseExecutor<Client, ClientApi> {
}
