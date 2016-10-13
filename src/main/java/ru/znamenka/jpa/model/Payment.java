package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "payments")
public class Payment implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    @Getter @Setter
    private Long id;

    @Column(name = "payment_amount")
    @Getter @Setter
    private Long paymentAmount;

    @Column(name = "payment_date")
    @Getter @Setter
    private Timestamp paymentDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    @Getter @Setter
    private Purchase purchase;

    @Column(name = "purchase_id")
    @Getter @Setter
    private Long purchaseId;
}
