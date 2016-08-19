package ru.znamenka.service.page.sale;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.repository.QueryFactory;

import java.util.List;

import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;

/**
 * Created by Сережа on 11.08.2016.
 */

@Service
public class SaleService {

    @Autowired
    private QueryFactory factory;

  /*  private List<Tuple> getPaymentsByPurchase(Long purchaseId){
        JPAQuery<Tuple> query = factory.getJpaQuery();
        query
                /// TODO: 11.08.2016 дописать селект

                .select(purchaseId,product.productName,purchase.discount,1)
                .from(purchase)
                .leftJoin(purchase.product, product)
              ;
                .where(purchase.id.eq(purchaseId).and(product.expireDays.gt(0)));

        return query.fetch();
    }
*/
}
