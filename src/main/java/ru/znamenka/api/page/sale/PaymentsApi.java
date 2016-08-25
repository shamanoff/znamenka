package ru.znamenka.api.page.sale;


import lombok.Builder;
import lombok.Getter;
import ru.znamenka.api.BaseApi;


/**
 * <p>
 *
 * Создан 11.08.2016
 * <p>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Builder
public class PaymentsApi implements BaseApi {

    @Getter
    private String productName;
    @Getter
    private Long purchaseId;
    @Getter
    private Double  paid;
    @Getter
    private Double remain;
}
