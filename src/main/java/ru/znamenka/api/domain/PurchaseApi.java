package ru.znamenka.api.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.api.BaseApi;

import java.sql.Date;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class PurchaseApi implements BaseApi {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private Byte isProvided;

    @Getter @Setter
    private Date purchaseDate;

    @Getter @Setter
    private boolean expired;

    @Getter @Setter
    private Long clientId;

    @Getter @Setter
    private Long productId;

    @Getter @Setter
    private Long trainerId;

    @Getter @Setter
    private Long discountId;

    public static PurchaseApi emptyPurchase() {
        return new PurchaseApi();
    }

}
