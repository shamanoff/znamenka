package ru.click.crm.represent.page.schedule;


import lombok.Builder;
import lombok.Getter;
import ru.click.core.represent.DomainApi;

/**
 * <p>
 * <p>
 * Создан 26.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Builder
public class SubscriptionApi implements DomainApi {

    @Getter
    private Long purchaseId;

    @Getter
    private String productName;
}
