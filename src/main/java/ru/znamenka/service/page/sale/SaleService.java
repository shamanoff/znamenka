package ru.znamenka.service.page.sale;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.znamenka.api.converter.page.sale.PaymentsApiConverter;
import ru.znamenka.api.page.sale.PaymentsApi;
import ru.znamenka.jpa.repository.QueryFactory;

import java.util.List;
import java.util.stream.Collectors;

import static ru.znamenka.jpa.model.QPayment.payment;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;

/**
 * Created by Сережа on 11.08.2016.
 */

@Service
public class SaleService {

    @Autowired
    private QueryFactory factory;

    @Autowired
    private PaymentsApiConverter converter;

    public List<PaymentsApi> getPurchasesByClients(Long clientId) {
        JPAQuery<Tuple> query = factory.getJpaQuery();
        query
                .select(
                        purchase.id,
                        product.productName,
                        JPAExpressions
                                .select(payment.paymentAmount.sum())
                                .from(payment)
                                .where(payment.purchase.id.eq(purchase.id)),
                        product.price.subtract(JPAExpressions
                                .select(payment.paymentAmount.sum())
                                .from(payment)
                                .where(payment.purchase.id.eq(purchase.id)))
                )
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(product.price.gt(JPAExpressions
                        .select(payment.paymentAmount.sum())
                        .from(payment)
                        .where(payment.purchase.id.eq(purchase.id))).and(purchase.client.id.eq(clientId)))
        ;
        return query.fetch().stream().map(converter::convert).collect(Collectors.toList());
    }

}
