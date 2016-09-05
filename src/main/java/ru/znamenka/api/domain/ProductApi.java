package ru.znamenka.api.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;

import javax.persistence.Column;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class ProductApi implements BaseApi {

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
