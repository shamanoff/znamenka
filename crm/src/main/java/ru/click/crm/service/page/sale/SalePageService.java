package ru.click.crm.service.page.sale;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import ru.click.crm.represent.page.sale.ClientDebtApi;
import ru.click.core.represent.impl.BaseExecutor;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.ONE;
import static com.querydsl.core.types.dsl.Expressions.ZERO;
import static ru.click.core.model.QDiscount.discount;
import static ru.click.core.model.QPayment.payment;
import static ru.click.core.model.QProduct.product;
import static ru.click.core.model.QPurchase.purchase;

/**
 * <p>Сервис для получения долгов клиента
 * <p>
 * Создан 11.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class SalePageService extends BaseExecutor<Tuple, ClientDebtApi> {

    /**
     * Возвращает список долгов клиента
     *
     * @param clientId уникальный идентификатор клиента
     * @return список долгов клиента
     * @see ClientDebtApi
     */
    public List<ClientDebtApi> getClientPayments(Long clientId) {
        List<Tuple> tuple = initQuery(clientId).fetch();

        return toApi(tuple);
    }

    /**
     * Инициализируем запрос, использую QueryDSL
     *
     * @param clientId уникальный идентификатор клиента
     * @return запрос для получения списка долгов клиента
     */
    private JPAQuery<Tuple> initQuery(Long clientId) {
        Expression<Long> alreadyPaid = JPAExpressions
                .select(payment.paymentAmount.sum().coalesce(ZERO))
                .from(payment)
                .where(payment.purchase.id.eq(purchase.id));

        NumberExpression<Double> priceWithDiscount = product
                .price
                .multiply(ONE.doubleValue().subtract(discount.discountAmount.coalesce(ZERO).asNumber().doubleValue().divide(100)));

        return getQuery()
                .select(
                        purchase.id, // id продажи
                        product.productName, // имя товара
                        alreadyPaid, // сумма, уже заплаченная клиентом за товар
                        priceWithDiscount.subtract(alreadyPaid) // осталось заплатить
                )
                .from(purchase)
                .leftJoin(purchase.product, product)
                .leftJoin(purchase.discount, discount)
                // брать продажи только те, за которых необходимо доплатить
                .where(priceWithDiscount.gt(alreadyPaid)
                        .and(purchase.client.id.eq(clientId))); // по id клиенту
    }

}
