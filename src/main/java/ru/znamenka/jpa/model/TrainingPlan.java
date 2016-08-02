package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "JF_training_plan")
public class TrainingPlan implements BaseModel<Long> {

    @Id
    @Column(name = "id_training_plan")
    @Getter @Setter
    private Long id;
    @Column(name = "repetitions_amount")
    @Getter @Setter
    private Integer repetitionsAmount;
    @Column(name = "exercise_id")
    @Getter @Setter
    private Long exerciseId;
}
