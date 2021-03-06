package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "duty_plan_type")
@Getter @Setter
public class DutyPlanType implements BaseModel<Long> {

    @Id
    @Column(name = "duty_plan_type_id")
    private Long id;

    @Column(name = "duty_plan_name")
    private String name;

}
