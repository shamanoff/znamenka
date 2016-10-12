package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "JF_purchase")
@NamedEntityGraph(
        name = "Purchase.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
                @NamedAttributeNode(value = "client")
        }
)
public class Purchase implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "purchase_id")
    @Getter @Setter
    private Long id;

    @Column(name = "is_provided")
    @Getter @Setter
    private Boolean isProvided;

    @Column(name = "purchase_date")
    @Getter @Setter
    private Date purchaseDate;

    @Column(name = "expired", nullable = false)
    @Getter
    private boolean expired;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @Getter @Setter
    private Client client;

    @Column(name = "client_id")
    @Getter @Setter
    private Long clientId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    @Getter @Setter
    private Product product;

    @Column(name = "product_id", nullable = false)
    @Getter @Setter
    private Long productId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id", insertable = false, updatable = false)
    @Getter @Setter
    private Trainer trainer;

    @Column(name = "trainer_id")
    @Getter @Setter
    private Long trainerId;

    @OneToMany(fetch = LAZY)
    @Getter @Setter
    private List<Training> trainings;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "discount_id", insertable = false, updatable = false)
    @Getter @Setter
    private Discount discount;

    @Column(name = "discount_id")
    @Getter @Setter
    private Long discountId;

    @OneToMany(mappedBy = "purchase",fetch = LAZY)
    @Getter @Setter
    private List<Payment> payments;

    private void setExpired(Boolean expired) {
        this.expired = expired != null && expired;
    }


}
