package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Deprecated
//@Entity(name = "JF_exercises")
public class Exercises implements BaseModel<Long> {

    @Id
    @Column(name = "exercise_id")
    @Getter @Setter
    private Long id;
    @Column(name = "description")
    @Getter @Setter
    private String description;
    @Column(name = "name")
    @Getter @Setter
    private String name;

}
