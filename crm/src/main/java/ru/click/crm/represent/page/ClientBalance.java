package ru.click.crm.represent.page;


import lombok.Getter;
import lombok.Setter;
import ru.click.core.represent.DomainApi;

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
@Getter
@Setter
public class ClientBalance implements DomainApi {


    private Long id;

    private String surname;

    private String phone;

    private String email;

    private Date birthDate;

    private String name;

    private Double balance;


}
