package ru.znamenka.service.report;


import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.repository.ExecutorQueries;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;

@Service
@Scope(SCOPE_PROTOTYPE)
public class TrainerPurchaseReport {

    // TODO: 03.08.2016 изменить на id пробной тренировки
    private static final Long TEST_PRODUCT_ID = 1L;

    private static final List<Long> PRODUCT_ID = asList(2L);

    @Autowired
    @Qualifier("facadeRepository")
    private EntityRepository repo;

    @Autowired
    @Qualifier("mainExecutor")
    protected ExecutorQueries executor;

    private final Long trainerId;


    public TrainerPurchaseReport(long trainerId) {
        this.trainerId = trainerId;
    }

    public double getProfitByTrainer() {
        JPAQuery<Double> query = executor.getJpaQuery();
        // // TODO: 03.08.2016 какой вид join использовать?
        query.select(product.price.sum()).from(purchase).innerJoin(purchase.product, product).where(purchase.trainer.id.eq(trainerId));
        Double sum = query.fetchOne();
        if (sum == null) return 0.0;
        Double profit = sum * getConversion();
        return profit;
    }


    private double getConversion() {
        Double persent = getPercent();

        if (persent <= 0.50) {
            return 0.03;
        }
        if (persent <= 75) {
            return 0.06;
        }
        return 0.1;
    }



    // TODO: 03.08.2016  добавить параметр дату (месяц)
    private double getPercent() {
        List<Purchase> purchases = getPurchaseTestTrainings();

        List<Long> listClientId = purchases.stream().map(Purchase::getClientId).collect(Collectors.toList());
        Predicate pTraining = purchase.product.id.in(PRODUCT_ID).and(purchase.client.id.in(listClientId));
        long countTrainings = repo.count(Purchase.class, pTraining);
        long countTestTraining = purchases.size();

        double count = countTrainings / countTestTraining;

        return count;
    }

    private List<Purchase> getPurchaseTestTrainings() {
        Predicate pTestTraining = purchase
                .product.id.eq(TEST_PRODUCT_ID)
                .and(purchase.trainer.id.eq(trainerId));
        return repo.findAll(Purchase.class, pTestTraining);
    }

}
