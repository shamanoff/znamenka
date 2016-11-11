package ru.znamenka.crm.represent.page.sale;


import lombok.Builder;
import lombok.Getter;
import ru.znamenka.jpa.represent.DomainApi;

/**
 * <p>Отображает информацию по платежам для
 * товаров, по которым есть долг
 * Создан 11.08.2016
 * <p>

 * @author Евгений Уткин (Eugene Utkin)
 */
@Builder
public class ClientDebtApi implements DomainApi {

    /**
     * Имя продукта
     */
    @Getter
    private String productName;
    /**
     * Уникальный идентификатор покупки
     */
    @Getter
    private Long purchaseId;
    /**
     * Уже заплачено
     */
    @Getter
    private Double  paid;
    /**
     * Осталось заплатить
     */
    @Getter
    private Double remain;
}
