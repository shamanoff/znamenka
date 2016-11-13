package ru.click.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * Создан 13.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Entity
@Table(name = "abon_type", schema = "common")
@Getter @Setter
public class AbonType implements BaseModel<Integer> {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;
}
