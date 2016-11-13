package ru.click.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "duty_plan_type", schema = "common")
@Getter @Setter
public class DutyPlanType implements BaseModel<Long> {

    @Id
    @Column(name = "duty_plan_type_id")
    private Long id;

    @Column(name = "duty_plan_name")
    private String name;

}
