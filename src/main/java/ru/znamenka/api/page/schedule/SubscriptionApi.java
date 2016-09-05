package ru.znamenka.api.page.schedule;

import lombok.Builder;
import lombok.Getter;
import ru.znamenka.api.BaseApi;

/**
 * <p>
 * <p>
 * Создан 26.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Builder
public class SubscriptionApi implements BaseApi {

    @Getter
    private Long purchaseId;

    @Getter
    private String productName;
}
