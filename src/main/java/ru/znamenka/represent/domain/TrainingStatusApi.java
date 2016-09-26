package ru.znamenka.represent.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.represent.DomainApi;

/**
 * <p>
 * <p>
 * Создан 19.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class TrainingStatusApi implements DomainApi {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;
}
