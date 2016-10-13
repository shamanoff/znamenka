package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.DiscriminatorType.INTEGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity(name = "products")
@Getter @Setter
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "is_abon", discriminatorType = INTEGER)
@DiscriminatorValue(value = "0")
public class Product implements BaseModel<Long> {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product", fetch = LAZY)
    private List<Purchase> purchases;

}
