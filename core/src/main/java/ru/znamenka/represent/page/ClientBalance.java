package ru.znamenka.represent.page;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.represent.DomainApi;

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
public class ClientBalance implements DomainApi {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String surname;

    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private Date birthDate;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Double balance;


}
