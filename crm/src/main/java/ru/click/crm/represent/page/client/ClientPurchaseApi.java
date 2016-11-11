package ru.click.crm.represent.page.client;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import ru.click.crm.represent.domain.PaymentApi;
import ru.click.core.represent.DomainApi;

import java.time.LocalDate;
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
    private final String priceDisc;
    @Getter
    @JsonFormat(pattern = "dd/MM/yyyy")
    private final LocalDate purchaseDate;
    @Getter
    private final String trainerName;
    @Getter
    private final String discountAmount;
    @Getter
    private final List<PaymentApi> payments;
}
