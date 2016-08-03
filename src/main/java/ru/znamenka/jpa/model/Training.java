package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

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
@Entity(name = "JF_TRAININGS")
@NamedEntityGraph(
        name = "Training.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
                @NamedAttributeNode(value = "client"),
                @NamedAttributeNode(value = "purchase")
        }
)
public class Training implements BaseModel<Long> {

    @Id
    @Column(name = "training_id")
    @Getter @Setter
    private Long id;

    @Column(name = "training_plan")
    @Getter @Setter
    private Long trainingPlan;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id")
    @Getter @Setter
    private Trainer trainer;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id")
    @Getter @Setter
    private Client client;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "purchase_id")
    @Getter @Setter
    private Purchase purchase;
}
