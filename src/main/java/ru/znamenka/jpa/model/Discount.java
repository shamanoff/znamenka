package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "discounts")
public class Discount implements BaseModel<Long> {

    @Id
    @Column(name = "discount_id")
    @Getter @Setter
    private Long id;

    @Column(name = "discount_amount", nullable = false)
    @Getter @Setter
    private int discountAmount;

    @OneToMany(fetch = LAZY)
    @Getter @Setter
    private List<Purchase> purchases;

}
