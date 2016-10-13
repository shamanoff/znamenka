package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity(name = "JF_products")
@Inheritance(strategy = TABLE_PER_CLASS)
public class Product implements BaseModel<Long> {

    @Id
    @Column(name = "product_id")
    @Getter @Setter
    private Long id;

    @Column(name = "product_name", nullable = false)
    @Getter @Setter
    @NonNull
    private String productName;

    @Column(name = "expire_days")
    @Getter @Setter
    private Integer expireDays;

    @Column(name = "price", nullable = false)
    @Getter @Setter
    @NonNull
    private Double price;

    @OneToMany(mappedBy = "product", fetch = LAZY)
    @Getter @Setter
    private List<Purchase> purchases;

}
