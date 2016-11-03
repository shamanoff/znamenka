package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "payments")
@Getter @Setter
public class Payment implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "payments_seq")
    @SequenceGenerator(name = "payments_seq", sequenceName = "payments_payment_id_seq", allocationSize = 1)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "payment_date")
    private Timestamp paymentDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    private Purchase purchase;

    @Column(name = "purchase_id")
    private Long purchaseId;
}
