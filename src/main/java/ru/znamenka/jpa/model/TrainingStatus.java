package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <p>
 * <p>
 * Создан 19.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Entity(name = "training_status")
public class TrainingStatus implements BaseModel<Long> {

    @Id
    @Column(name = "status_id", nullable = false, unique = true)
    @Getter @Setter
    private Long id;

    @Column(name = "status_name", nullable = false, unique = true)
    @Getter @Setter
    private String name;

    @Override
    public String toString() {
        return getName();
    }
}
