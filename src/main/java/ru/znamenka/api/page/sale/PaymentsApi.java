package ru.znamenka.api.page.sale;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;


/**
 * Created by Сережа on 11.08.2016.
 */


@Builder
public class PaymentsApi implements BaseApi {
    @Getter
    private String abonement;
    @Getter
    private Long purchaseId;
    @Getter
    private Double  paid;
    @Getter
    private Double remain;
}
