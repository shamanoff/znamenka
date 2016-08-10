package ru.znamenka.api.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;
import ru.znamenka.jpa.model.BaseModel;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.model.Trainer;

import javax.persistence.*;

import java.security.Timestamp;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

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

public class TrainingApi implements BaseApi {



    @Getter @Setter
    private Long Id;

    @Getter @Setter
    private Long trainer;

    @Getter @Setter
    private Timestamp start;


    @Getter @Setter
    private Long client;


    @Getter @Setter
    private Long purchase;
}
