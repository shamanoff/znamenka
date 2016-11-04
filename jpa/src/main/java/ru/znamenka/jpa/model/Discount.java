package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "discounts", schema = "common")
@Getter @Setter
public class Discount implements BaseModel<Long> {

    @Id
    @Column(name = "discount_id")
    private Long id;

    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    @OneToMany(fetch = LAZY)
    private List<Purchase> purchases;

}
