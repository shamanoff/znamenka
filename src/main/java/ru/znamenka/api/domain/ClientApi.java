package ru.znamenka.api.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import ru.znamenka.api.BaseApi;

import javax.validation.constraints.Pattern;
import java.sql.Date;

public class ClientApi implements BaseApi {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    @Pattern(regexp = "^[A-zА-я]+")
    private String fname;
    @Getter @Setter
    @Pattern(regexp = "^[A-zА-я]+")
    private String sname;
    @Getter @Setter
    @Pattern(regexp = "^7[0-9]{10}")
    private String phone;
    @Getter @Setter
    @Email
    private String email;
    @Getter @Setter
    private Date birthDate;
    @Getter @Setter
    private Boolean male;
    @Getter @Setter
    private String comment;


}
