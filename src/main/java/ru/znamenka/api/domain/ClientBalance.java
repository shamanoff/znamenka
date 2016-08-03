package ru.znamenka.api.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;

/**
 * <p>
 * <p>
 * Создан 03.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class ClientBalance implements BaseApi {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String fname;
    @Getter @Setter
    private String lname;
    @Getter @Setter
    private Double balance = 10.0;
}
