package ru.click.crm.represent.domain;


import lombok.Builder;
import lombok.Getter;
import ru.click.core.represent.DomainApi;

@Builder
public class DiscountApi implements DomainApi {

    @Getter
    private Long id;

    @Getter
    private int amount;
}
