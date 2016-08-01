package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * <p>
 * <p>
 * Создан 01.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Entity(name = "jf_clients")
public class Client implements BaseModel<Long> {

    @Id
    @Column(name = "client_id")
    @Getter @Setter
    private Long id;

    @Column(name = "client_name")
    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "client", fetch = LAZY)
    @Getter @Setter
    private List<Training> trainings;
}
