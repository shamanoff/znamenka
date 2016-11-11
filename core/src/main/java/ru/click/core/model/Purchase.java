package ru.click.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "purchase")
@NamedEntityGraph(
        name = "Purchase.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
                @NamedAttributeNode(value = "client")
        }
)
@Getter
@Setter
public class Purchase implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "purchase_seq")
    @SequenceGenerator(name = "purchase_seq", sequenceName = "purchase_purchase_id_seq", allocationSize = 1)
    @Column(name = "purchase_id")
    private Long id;

    @Column(name = "is_provided")
    private Boolean isProvided;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @Column(name = "client_id")

    private Long clientId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)

    private Product product;

    @Column(name = "product_id", nullable = false)

    private Long productId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id", insertable = false, updatable = false)

    private Trainer trainer;

    @Column(name = "trainer_id")

    private Long trainerId;

    @OneToMany(fetch = LAZY)

    private List<Training> trainings;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "discount_id", insertable = false, updatable = false)
    private Discount discount;

    @Column(name = "discount_id")
    private Long discountId;

    @OneToMany(mappedBy = "purchase", fetch = LAZY)
    private List<Payment> payments;


}
