package ru.znamenka.service.report;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.repository.ExecutorQueries;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;
import static ru.znamenka.util.BDFactory.bd;

@Service
@Scope(SCOPE_PROTOTYPE)
public class TrainerPurchaseReport {

    private static final Long testTrainingId = 1L;

    private static final List<Long> trainingIds = asList(2L);

    private EntityRepository repo;

    private ExecutorQueries executor;

    private final Long trainerId;

    private final LocalDate date;


    public TrainerPurchaseReport(long trainerId, LocalDate date) {
        this.trainerId = trainerId;
        this.date = date;
    }

    /**
     * Вычисляет прибыль тренера от продаж за выбранный месяц
     *
     * @return сумма прибыли
     */
    public double getProfitByTrainer() {
        JPAQuery<Double> query = executor.getJpaQuery();
        query
                .select(product.price.sum())
                .from(purchase)
                .innerJoin(purchase.product, product)
                .where(pTrainerId()
                        .and(pCurMonth())
                );
        Double sum = query.fetchOne();
        if (sum == null) return 0.0;
        val profit = bd(sum).multiply(getConversion());
        return profit.doubleValue();
    }


    /**
     * Вычисляет конверсию тренера за выбранный месяц
     *
     * @return конверсия тренера
     */
    private BigDecimal getConversion() {
        double percent = getPercent();
        if (percent <= 0.50) {
            return new BigDecimal(0.03);
        }
        if (percent <= 0.75) {
            return new BigDecimal(0.06);
        }
        return new BigDecimal(0.1);
    }


    /**
     * Метод для получения процента покупки
     * тренировок после пробной тренировки
     * у конкретного тренера за заданный месяц
     *
     * @return отношение тренировок
     */
    private double getPercent() {
        List<Purchase> purchases = getPurchaseTestTrainings();
        // получаем список id всех клиентов, которые приобрели пробные тренировки из списка покупок
        List<Long> listClientId = purchases.stream().map(Purchase::getClientId).distinct().collect(toList());
        // из списка клиентов строим предикат, который для поиска покупкок обычных тренировок клиентов
        Predicate pClientWithTrainingPurchases = pTraining()
                .and(pCurMonth())
                .and(pTrainerId())
                .and(purchase.client.id.in(listClientId));
        long countTrainings = repo.count(Purchase.class, pClientWithTrainingPurchases);
        long countTestTraining = purchases.size();
        val d1 = bd(countTrainings);
        val d2 = bd(countTestTraining);
        return d1.divide(d2, ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * Получаем все покупки пробных тренировок по id тренера
     * и месяца
     *
     * @return список покупок ({@link Purchase})
     * @see this#trainerId
     * @see this#date
     */
    private List<Purchase> getPurchaseTestTrainings() {
        Predicate pTestTraining = pTestTraining().and(pTrainerId()).and(pCurMonth());
        return repo.findAll(Purchase.class, pTestTraining);
    }

    /**
     * Строит предикат для поиска тренера по заданному id
     *
     * @return предикат
     */
    private BooleanExpression pTrainerId() {
        return purchase.trainer.id.eq(trainerId);
    }

    /**
     * Строит предикат для поиска пробных тренировок
     *
     * @return предикат
     */
    private BooleanExpression pTestTraining() {
        return purchase.product.id.eq(testTrainingId);
    }

    /**
     * Строит предикат для поиска тренеровок
     *
     * @return предикат
     */
    private BooleanExpression pTraining() {
        return purchase.product.id.in(trainingIds);
    }

    /**
     * Строит предикат для поиска по номеру месяца
     *
     * @return предикат
     */
    private BooleanExpression pCurMonth() {
        return purchase.purchaseDate.month().eq(date.getMonthValue())
                .and(purchase.purchaseDate.year().eq(date.getYear()));
    }

    @Autowired
    public TrainerPurchaseReport repository(@Qualifier("facadeRepository") EntityRepository repo) {
        this.repo = repo;
        return this;
    }

    @Autowired
    public TrainerPurchaseReport setExecutor(@Qualifier("mainExecutor") ExecutorQueries executor) {
        this.executor = executor;
        return this;
    }

}
