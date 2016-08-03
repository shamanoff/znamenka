package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity(name = "JF_PURCHASE")
@NamedEntityGraph(
        name = "Purchase.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
                @NamedAttributeNode(value = "client")
        }
)
public class Purchase implements BaseModel<Long> {

    @Id
    @Column(name = "purchase_id")
    @Getter @Setter
    private Long id;

    @Column(name = "is_provided")
    @Getter @Setter
    private Byte isProvided;

    @Column(name = "purchase_date")
    @Getter @Setter
    private Date purchaseDate;

    @Column(name = "expired")
    @Getter @Setter
    private boolean expired;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id")
    @Getter @Setter
    private Client client;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    @Getter @Setter
    private Product product;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id")
    @Getter @Setter
    private Trainer trainer;

    @OneToMany(fetch = LAZY)
    @Getter @Setter
    private List<Training> trainings;

    public Long getClientId() {
        return getClient() == null ? null : getClient().getId();
    }

    public Long getProductId() {
       return getProduct() == null ? null : getProduct().getId();
    }

    public Long getTrainerId() {
        return getTrainer() == null ? null : getTrainer().getId();
    }

}
