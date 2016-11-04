package ru.znamenka.jpa.repository.domain;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.repository.QueryDslRepository;

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
