package ru.znamenka.api.domain;

import lombok.Builder;
import lombok.Getter;

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
public class TrainerApi {
    @Getter
    private final Long id;

    @Getter
    private final String name;
}
