package ru.znamenka.crm.represent.domain;


import lombok.Builder;
import lombok.Getter;
import ru.znamenka.jpa.represent.DomainApi;

@Builder
public class DiscountApi implements DomainApi {

    @Getter
    private Long id;

    @Getter
    private int amount;
}
