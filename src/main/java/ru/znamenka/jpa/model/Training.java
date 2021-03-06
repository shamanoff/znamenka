package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.jpa.converter.BooleanConverter;
import ru.znamenka.jpa.converter.LocalDateTimeConverter;
import ru.znamenka.jpa.listener.CreatedTrainingListener;

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
@Entity(name = "trainings")
@NamedEntityGraph(
        name = "Training.Graph",
        attributeNodes = {
                @NamedAttributeNode(value = "trainer"),
                @NamedAttributeNode(value = "client"),
                @NamedAttributeNode(value = "purchase"),
                @NamedAttributeNode(value = "status")
        }
)
@EntityListeners({CreatedTrainingListener.class})
@Getter @Setter
public class Training implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "training_id")
    private Long id;

    @Column(name = "start")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime start;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id", updatable = false, insertable = false)
    private Trainer trainer;

    @Column(name = "trainer_id")
    private Long trainerId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    private Client client;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "purchase_id", updatable = false, insertable = false)
    private Purchase purchase;

    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "status_id", updatable = false, insertable = false)
    private TrainingStatus status;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "comment")
    private String comment;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "pass_for_auto")
    private Boolean passForAuto;
}
