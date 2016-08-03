package ru.znamenka.service.report;


import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.repository.EntityRepository;

import static ru.znamenka.jpa.model.QPurchase.purchase;

@Service
public class TrainerPurchaseReport {

    // TODO: 03.08.2016 изменить на id пробной тренировки
    private static final Long TEST_PRODUCT_ID = 1L;

    @Autowired
    @Qualifier("facadeRepository")
    private EntityRepository repo;


    // TODO: 03.08.2016  добавить параметр дату (месяц)
    public long getCountTestTrainings(Long trainerId) {
        Predicate predicate = purchase.productId.eq(TEST_PRODUCT_ID).and(purchase.trainerId.eq(trainerId));
        return repo.count(Purchase.class, predicate);
    }
}
