package ru.click.crm.represent.domain;


import lombok.Getter;
import lombok.Setter;
import ru.click.core.represent.DomainApi;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter @Setter
public class ProductApi implements DomainApi {

    private Long id;

    private String name;

    private Double price;
}
