package ru.znamenka.represent.domain;

import lombok.Builder;
import lombok.Getter;
import ru.znamenka.represent.DomainApi;

@Builder
public class DiscountApi implements DomainApi {

    @Getter
    private Long id;

    @Getter
    private int amount;
}
