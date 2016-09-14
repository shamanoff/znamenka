package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "JF_clients")
public class Client implements BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "client_id")
    @Getter @Setter
    private Long id;

    @Column(name = "surname")
    @Getter @Setter
    private String surname;

    @Column(name = "phone")
    @Getter @Setter
    private String phone;

    @Getter @Setter
    @Column(name = "email")
    private String email;

    @Getter @Setter
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "male")
    @Getter @Setter
    private Boolean male;

    @Column(name = "comment")
    @Getter @Setter
    private String comment;

    @OneToMany(mappedBy = "client", fetch = LAZY)
    @Getter @Setter
    private List<Training> trainings;

    @OneToMany(mappedBy = "client", fetch = LAZY)
    @Getter @Setter
    private List<Purchase> purchases;

}
