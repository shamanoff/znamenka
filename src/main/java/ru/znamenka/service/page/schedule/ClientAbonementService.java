package ru.znamenka.service.page.schedule;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.repository.QueryFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;

@Service
public class ClientAbonementService {

    @Autowired
    private QueryFactory factory;

    public Map<Long, String> getAbonementByClient(Long clientId) {
        JPAQuery<Tuple> query = factory.getJpaQuery();
        query
                .select(purchase.id, product.productName)
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(purchase.client.id.eq(clientId).and(product.id.in(1,2,3)));
        List<Tuple> tuples = query.fetch();

        Map<Long, String> abonements = new HashMap<>(tuples.size());
        for (Tuple tuple : tuples) {
            abonements.put(tuple.get(purchase.id), tuple.get(product.productName));
        }
       return abonements;
    }
}
