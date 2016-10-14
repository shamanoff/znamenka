package ru.znamenka.jpa.model;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.DiscriminatorType.CHAR;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity(name = "products")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "is_abon", discriminatorType = CHAR)
@DiscriminatorValue(value = "f")
public class Product implements BaseModel<Long> {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product", fetch = LAZY)
    private List<Purchase> purchases;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
