package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 */
@Deprecated
//@Entity(name = "JF_duty_plan_type")
public class DutyPlanType implements BaseModel<Long> {

    @Id
    @Column(name = "duty_plan_type_id")
    @Getter @Setter
    private Long id;
    @Column(name = "duty_plan_name")
    @Getter @Setter
    private Long dutyPlanName;

}
