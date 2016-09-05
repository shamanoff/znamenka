package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.EAGER;

@Deprecated
//@Entity(name = "JF_duty_shedule")
public class DutyShedule implements BaseModel<Long> {

    @Id
    @Column(name = "duty_id")
    @Getter @Setter
    private Long id;
    @Column(name = "trainer_id")
    @Getter @Setter
    private Long trainerId;
    @Column(name = "datetime_start")
    @Getter @Setter
    private Timestamp datetimeStart;
    @Column(name = "datetime_end")
    @Getter @Setter
    private Timestamp datetimeEnd;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "duty_plan_type")
    @Getter @Setter
    private DutyPlanType type;

}
