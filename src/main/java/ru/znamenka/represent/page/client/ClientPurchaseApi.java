package ru.znamenka.represent.page.client;

import lombok.Builder;
import lombok.Getter;
import ru.znamenka.represent.DomainApi;
import ru.znamenka.represent.domain.PaymentApi;

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
public class ClientPurchaseApi implements DomainApi {

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
