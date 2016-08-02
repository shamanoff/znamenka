package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "JF_duty_shedule")
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
    @Column(name = "duty_plan_type")
    @Getter @Setter
    private Integer dutyPlanType;

}
