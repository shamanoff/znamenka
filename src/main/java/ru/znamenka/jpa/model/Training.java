package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

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
@Entity(name = "JF_trainings")
@NamedEntityGraph(
        name = "Training.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
                @NamedAttributeNode(value = "client"),
                @NamedAttributeNode(value = "purchase"),
                @NamedAttributeNode(value = "status")
        }
)
public class Training implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "training_id")
    @Getter @Setter
    private Long id;

    @Column(name = "training_plan")
    @Getter @Setter
    private Long trainingPlan;

    @Column(name = "start")
    @Getter @Setter
    private Timestamp start;

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

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "status_id")
    @Getter @Setter
    private TrainingStatus status;
}
