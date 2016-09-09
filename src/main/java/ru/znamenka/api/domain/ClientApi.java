package ru.znamenka.api.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import ru.znamenka.api.BaseApi;

import java.sql.Date;

public class ClientApi implements BaseApi {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    @NotEmpty
    private String fname;
    @Getter @Setter
    @NotEmpty
    private String sname;
    @Getter @Setter
    private String phone;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private Date birthDate;


}
