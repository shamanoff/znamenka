package ru.znamenka.api.custom;

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
public class TrainingReportApi {

    @Getter
    private final String tranerName;

    @Getter
    private final String clientName;
}
