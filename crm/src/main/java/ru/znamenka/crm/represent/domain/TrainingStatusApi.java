package ru.znamenka.crm.represent.domain;


import lombok.Getter;
import lombok.Setter;
import ru.znamenka.crm.represent.DomainApi;

/**
 * <p>
 * <p>
 * Создан 19.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter @Setter
public class TrainingStatusApi implements DomainApi {


    private Long id;

    private String name;
}
