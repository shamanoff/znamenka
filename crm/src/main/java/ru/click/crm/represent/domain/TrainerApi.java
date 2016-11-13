package ru.click.crm.represent.domain;


import lombok.Builder;
import lombok.Getter;
import ru.click.core.represent.DomainApi;

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
@Builder @Getter
public class TrainerApi implements DomainApi {

    private final Long id;
    private final String name;
}
