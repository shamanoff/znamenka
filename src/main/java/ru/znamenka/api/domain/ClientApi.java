package ru.znamenka.api.domain;


import lombok.Builder;
import lombok.Getter;
import ru.znamenka.api.BaseApi;

import java.sql.Date;

@Builder
public class ClientApi implements BaseApi {

    @Getter
    private final Long id;
    @Getter
    private final String name;
    @Getter
    private Integer phone;
    @Getter
    private String email;
    @Getter
    private Date birthDate;





}
