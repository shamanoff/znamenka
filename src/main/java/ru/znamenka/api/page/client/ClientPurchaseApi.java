package ru.znamenka.api.page.client;

import lombok.Builder;
import lombok.Getter;
import ru.znamenka.api.BaseApi;
import ru.znamenka.api.domain.PaymentApi;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * <p>
 * Создан 13.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Builder
public class ClientPurchaseApi implements BaseApi {

    @Getter
    private final String productName;
    @Getter
    private final String price;
    @Getter
    private final String paid;
    @Getter
    private final Timestamp purchaseDate;
    @Getter
    private final String trainerName;
    @Getter
    private final String discountAmount;

    @Getter
    private final List<PaymentApi> payments;
}
