package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

/**
 * <p>
 * Создан 13.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Entity(name = "abonements")
@DiscriminatorValue("t")
@Getter @Setter
public class Abonement extends Product {

    @Column(name = "training_count")
    private Integer trainingCount;

    @Column(name = "expire_days")
    private Integer expireDays;

    @Column(name = "abon_type")
    private Integer abonTypeId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "abon_type", insertable = false, updatable = false)
    private AbonType abonType;

}
