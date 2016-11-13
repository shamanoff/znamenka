package ru.click.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * <p>
 * <p>
 * Создан 01.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Entity
@Table(name = "trainers", schema = "common")
@Getter @Setter
public class Trainer implements BaseModel<Long> {

    @Id
    @Column(name = "trainer_id")
    private Long id;

    @Column(name = "trainer_name")
    private String name;

    @OneToMany(mappedBy = "trainer", fetch = LAZY)
    private List<Training> trainings;

    @OneToMany(mappedBy = "trainer", fetch = LAZY)
    private List<Purchase> purchases;

}
