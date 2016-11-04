package ru.znamenka.crm.represent.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.znamenka.crm.represent.DomainApi;

@Getter
@Setter
@Accessors(chain = true)
public class PlanTypeApi implements DomainApi {

    private Long id;

    private String name;
}
