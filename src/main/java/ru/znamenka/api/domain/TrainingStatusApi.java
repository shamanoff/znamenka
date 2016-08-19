package ru.znamenka.api.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;

/**
 * <p>
 * <p>
 * Создан 19.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class TrainingStatusApi implements BaseApi {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;
}
