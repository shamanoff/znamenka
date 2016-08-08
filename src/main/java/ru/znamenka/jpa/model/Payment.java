package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "JF_payments")
public class Payment implements BaseModel<Long> {

    @Id
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
    @JoinColumn(name = "purchase_id")
    @Getter @Setter
    private Purchase purchase;

    private Long getPurchaseId() {
        return purchase == null ? null : purchase.getId();
    }
}
