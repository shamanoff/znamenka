package ru.znamenka.crm.represent.domain;


import lombok.Getter;
import lombok.Setter;
import ru.znamenka.jpa.represent.DomainApi;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter @Setter
public class PurchaseApi implements DomainApi {

    private Long id;

    private Boolean isProvided;

    private Date purchaseDate;

    private Boolean expired;

    @NotNull
    private Long clientId;

    @NotNull
    private Long productId;

    @NotNull
    private Long trainerId;

    private Long discountId;

    public static PurchaseApi emptyPurchase() {
        return new PurchaseApi();
    }

}
