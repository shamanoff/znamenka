package ru.znamenka.crm.service.report;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.model.Trainer;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.repository.QueryFactory;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;
import static ru.znamenka.crm.util.BDFactory.bd;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;

/**
 * todo разобраться с бизнес процессом
 */
@Service
@Scope(SCOPE_PROTOTYPE)
public class TrainerPurchaseReport {

    /**
     * Уникальный идентификатор пробной тренеровки в таблице PRODUCTS
     * ({@link ru.znamenka.jpa.model.Product#id}
     */
    private static final Long testTrainingId = 1L;
    /**
     * Список уникальных идентификаторов тренеровок в таблице PRODUCTS
     * ({@link ru.znamenka.jpa.model.Product#id}
     */
    private static final List<Long> trainingIds = Arrays.asList(2L);

    private static final int MOTH_OFFSET = 2;

    /**
     * Репозиторий для доступа к данным
     */
    private EntityRepository repo;

    /**
     * Фабрика запросов
     */
    private QueryFactory queryFactory;

    /**
     * Уникальный идентификатор тренера, для которого ведется расчет прибыли
     * от продаж
     */
    private final Long trainerId;

    private final LocalDate date;

    /**
     *<p>Конструктор, который создает экземляр класс для расчета
     * прибыли от продаж заданного тренера за опреленный месяц.
     * @param trainerId уникальный идентификатор тренера
     * @param date месяц, за который необходимо посчитать прибыль
     * @throws IllegalArgumentException если такого тренера не существует, либо дата равна {@literal null}
     */
    public TrainerPurchaseReport(long trainerId, LocalDate date) {
        notNull(date, "Date is required; it must not be null");
        this.trainerId = trainerId;
        this.date = date;
    }

    /**
     * Проверяет, корректна ли прошла инициализация объекта,
     * и существует ли тренер, прибыль которого необходимо расчитать
     */
    @PostConstruct
    private void validate() {
        notNull(repo, "Repository is required; it must not be null");
        notNull(queryFactory, "Query Factory is required; it must not be null");
        isTrue(repo.exists(Trainer.class, trainerId), "Trainer must be exists");
    }

    /**
     * Вычисляет прибыль тренера от продаж за выбранный месяц
     * по формуле "сумма продаж * конверсия"
     *
     * @return сумма прибыли
     */
    public double getProfit() {
        Double sum = getSum();
        if (sum == null) return 0.0;
        val profit = bd(sum).multiply(getConversion());
        return profit.doubleValue();
    }

    /**
     * Создает и выполянет запрос для получения суммы
     * от всех продаж тренера за месяц
     * @return сумма прибыли
     */
    private Double getSum() {
        JPAQuery<Double> query = queryFactory.getJpaQuery();
        query
                .select(product.price.sum())
                .from(purchase)
                .innerJoin(purchase.product, product)
                .where(pTrainerId()
                        .and(pCurMonth())
                );
        return query.fetchOne();
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
        // в этом и двух следующих месяцев
        Predicate pClientWithTrainingPurchases = pTraining()
                .and(purchase.client.id.in(listClientId))
                .and(pCurMonth(MOTH_OFFSET));
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

    private BooleanExpression pCurMonth(int offset) {
        return purchase.purchaseDate.month().between(date.getMonthValue(), date.getMonthValue() + offset)
                .and(purchase.purchaseDate.year().eq(date.getYear()));
    }

    /**
     * Сеттер для репозитория
     * @param repo репозиторий
     * @return ссылка на самого себя
     */
    @Autowired
    public TrainerPurchaseReport repository(@Qualifier("facadeRepository") EntityRepository repo) {
        this.repo = repo;
        return this;
    }

    /**
     * Сеттер для исполнителя запросов
     * @param queryFactory исполнитель запросов
     * @return ссылка на самого себя
     */
    @Autowired
    public TrainerPurchaseReport queryFactory(@Qualifier("mainExecutor") QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
        return this;
    }

}
