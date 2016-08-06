package ru.znamenka.api.custom;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;

import java.sql.Date;

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
    private String surname;

    @Getter @Setter
    private Integer phone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private Date birthDate;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Double balance;


}
