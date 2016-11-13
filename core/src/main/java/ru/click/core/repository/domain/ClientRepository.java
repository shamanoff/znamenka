package ru.click.core.repository.domain;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ru.click.core.model.Client;
import ru.click.core.repository.QueryDslRepository;

import java.util.List;

/**
 * <p>
 * <p>
 * Создан 01.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface ClientRepository extends QueryDslRepository<Client, Long> {

    @Override
    @Cacheable("allClients")
    List<Client> findAll();

    @Override
    @CacheEvict("allClients")
    Client save(Client client);

}
