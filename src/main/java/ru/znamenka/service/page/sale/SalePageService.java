package ru.znamenka.service.page.sale;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import ru.znamenka.represent.page.sale.ClientDebtApi;
import ru.znamenka.service.BaseExecutor;

import java.util.List;

import static ru.znamenka.jpa.model.QPayment.payment;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;

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
                .select(payment.paymentAmount.sum().coalesce(0L))
                .from(payment)
                .where(payment.purchase.id.eq(purchase.id));

        return getQuery()
                .select(
                        purchase.id, // id продажи
                        product.productName, // имя товара
                        alreadyPaid, // сумма, уже заплаченная клиентом за товар
                        product.price.subtract(alreadyPaid) // осталось заплатить
                )
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(product.price.gt(alreadyPaid) // брать продажи только те, за которых необходимо доплатить
                        .and(purchase.client.id.eq(clientId))); // по id клиенту
    }

}
