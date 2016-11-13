package ru.click.core.model;

import lombok.Getter;
import lombok.Setter;
import ru.click.core.converter.LocalDateTimeConverter;
import ru.click.core.listener.DutyListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "duty_schedule")
@EntityListeners({DutyListener.class})
@Getter @Setter
public class DutySchedule implements BaseModel<Long> {

    @Id
    @Column(name = "duty_id")
    @GeneratedValue(strategy = SEQUENCE, generator = "duty_schedule_seq")
    @SequenceGenerator(name = "duty_schedule_seq", sequenceName = "duty_schedule_duty_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "planned_start")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime plannedStart;

    @Column(name = "planned_end")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime plannedEnd;

    @Column(name = "fact_start")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime factStart;

    @Column(name = "fact_end")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime factEnd;

    @Column(name = "duty_plan_type")
    private Long planTypeId;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "duty_plan_type", updatable = false, insertable = false)
    private DutyPlanType planType;

    @Transient
    private String planTypeName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trainer_id", updatable = false, insertable = false)
    private Trainer trainer;

    @Column(name = "trainer_id")
    private Long trainerId;
}
