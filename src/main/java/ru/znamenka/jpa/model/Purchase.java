package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "JF_purchase")
public class Purchase implements BaseModel<Long> {

    @Id
    @Column(name = "purchase_id")
    @Getter @Setter
    private Long id;
    @Column(name = "is_provided")
    @Getter @Setter
    private Byte isProvided;
    @Column(name = "client_id")
    @Getter @Setter
    private Long clientId;
    @Column(name = "purchase_date")
    @Getter @Setter
    private Date purchaseDate;
    @Column(name = "product_id")
    @Getter @Setter
    private Long productId;
    @Column(name = "trainer_id")
    @Getter @Setter
    private Long trainerId;
    @Column(name = "expired")
    @Getter @Setter
    private boolean expired;

}
