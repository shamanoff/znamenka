package ru.znamenka.represent.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.represent.DomainApi;

import javax.persistence.Column;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class ProductApi implements DomainApi {

    @Getter @Setter
    private Long id;

    @Column(name = "product_name", nullable = false)
    @Getter @Setter
    private String name;

    @Getter @Setter
    private Integer expireDays;

    @Getter @Setter
    private Double price;
}
