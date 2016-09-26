package ru.znamenka.represent.domain;

import lombok.Builder;
import lombok.Getter;
import ru.znamenka.represent.DomainApi;

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
@Builder
public class TrainerApi implements DomainApi {

    @Getter
    private final Long id;

    @Getter
    private final String name;
}
