package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <p>
 * Создан 13.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Entity(name = "abon_type")
@Getter @Setter
public class AbonType implements BaseModel<Integer> {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;
}
