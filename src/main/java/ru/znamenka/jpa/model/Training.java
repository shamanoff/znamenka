package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime start;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id", updatable = false, insertable = false)
    @Getter @Setter
    private Trainer trainer;

    @Column(name = "trainer_id")
    @Getter @Setter
    private Long trainerId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    @Getter @Setter
    private Client client;

    @Column(name = "client_id")
    @Getter @Setter
    private Long clientId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "purchase_id", updatable = false, insertable = false)
    @Getter @Setter
    private Purchase purchase;

    @Column(name = "purchase_id")
    @Getter @Setter
    private Long purchaseId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "status_id", updatable = false, insertable = false)
    @Getter @Setter
    private TrainingStatus status;

    @Column(name = "status_id")
    @Getter @Setter
    private Long statusId;
}
