package ru.znamenka.api.domain;

import lombok.Builder;
import lombok.Getter;
import ru.znamenka.api.BaseApi;

@Builder
public class DiscountApi implements BaseApi {

    @Getter
    private Long id;

    @Getter
    private int amount;
}
