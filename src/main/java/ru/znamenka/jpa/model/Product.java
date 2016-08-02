package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "JF_products")
public class Product implements BaseModel<Long> {

    @Id
    @Column(name = "product_id")
    @Getter @Setter
    private Long id;
    @Column(name = "product_name")
    @Getter @Setter
    private Long productName;
    @Column(name = "expire_days")
    @Getter @Setter
    private Integer expireDays;
    @Column(name = "price")
    @Getter @Setter
    private Integer price;

}
