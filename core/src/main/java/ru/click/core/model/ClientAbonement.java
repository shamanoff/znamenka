package ru.click.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "clients_abonements")
@Getter
public class ClientAbonement implements BaseModel<Long> {

    @Id
    @Column(name = "id", insertable = false)
    @Setter
    private Long id;

    @Column(name = "product_id", insertable = false)
    private Long productId;

    @Column(name = "client_id", insertable = false)
    private Long clientId;

    @Column(name = "purchase_id", insertable = false)
    private Long purchaseId;

    @Column(name = "training_count", insertable = false)
    private Integer trainingCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "purchase_id", insertable = false, updatable = false)
    private Purchase purchase;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Abonement product;

}
