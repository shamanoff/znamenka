package ru.znamenka.service.page.schedule;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.api.page.schedule.SubscriptionApi;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.model.Trainer;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.service.page.BaseExecutor;
import ru.znamenka.util.locale.ExtMessageSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.util.Collections.singletonList;
import static org.springframework.util.Assert.notNull;
import static ru.znamenka.jpa.model.QPayment.payment;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;
import static ru.znamenka.util.Utils.googleTime;

/**
 * <p>
 * Сервис для страницы Расписание, отдает активные
 * абонементы по уникальному идентификатору клиента
 * <p>
 * Создан 04.07.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class SubscriptionPageService extends BaseExecutor<Tuple, SubscriptionApi> {

    /**
     * Google календарь
     */
    private final Calendar calendar;

    /**
     * Репозиторий бизнес-моделей
     */
    private final EntityRepository repo;

    /**
     * Источник сообщений
     */
    private final ExtMessageSource message;

    @Value("${calendar.id:primary}")
    private String calendarId;

    /**
     * Уникальные идентификаторы абонементов в таблице продуктов
     */
    private static final Number[] SUBSCRIPTIONS = new Long[]{1L, 2L, 3L};

    /**
     * Конструктор
     *
     * @param repo     репозиторий бизнес-моделей
     * @param calendar google календарь
     * @param message  источник сообщений
     */
    @Autowired
    public SubscriptionPageService(@Qualifier("facadeRepository") EntityRepository repo,
                                   Calendar calendar,
                                   ExtMessageSource message
    ) {
        notNull(repo);
        notNull(calendar);
        notNull(message);
        this.repo = repo;
        this.calendar = calendar;
        this.message = message;
    }

    /**
     * Возвращает список активных абонементов
     * @param clientId уникальный идентификатор клиента
     * @return список абонементов
     */
    public List<SubscriptionApi> getSubscriptionByClientId(Long clientId) {
        List<Tuple> tuple = initSubScrQuery(clientId).fetch();
        return toApi(tuple);
    }

    /**
     * Инициализирует SQL запрос
     * @param clientId уникальный идентификатор клиента
     * @return запрос
     */
    private JPAQuery<Tuple> initSubScrQuery(Long clientId) {
        return getQuery()
                .select(purchase.id, product.productName)
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(purchase.client.id.eq(clientId)
                        .and(product.id.in(SUBSCRIPTIONS))
                        .and(product.expireDays.gt(0)));
    }

    // // TODO: 11.08.2016 если нет таких клиентов и тренеров

    /**
     * Постит сохраненную тренировку в гугль календарь
     * @param training тренеровка
     * @return событие в гугль календаре
     * @throws IOException если возникли проблемы с отправкой запроса
     */
    public Event postToCalendar(TrainingApi training) throws IOException {
        Event event = new Event();
        event.setCreated(googleTime(training.getStart()));

        EventDateTime start = new EventDateTime()
                .setDateTime(googleTime(training.getStart()))
                .setTimeZone("Europe/Moscow");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(googleTime(training.getStart().toLocalDateTime().plus(1L, HOURS)));
        event.setEnd(end);
        Trainer trainer = repo.findOne(Trainer.class, training.getTrainerId());
        event.setSummary(trainer != null ? trainer.getName() : message.getMessage("schedule.trainer.not_found"));
        Client client = repo.findOne(Client.class, training.getClientId());
        EventAttendee attendee = new EventAttendee();
        attendee.setEmail(client.getEmail());
        attendee.setDisplayName(client.getName() + " " + client.getSurname());
        event.setAttendees(singletonList(attendee));
        event.setDescription(message.getMessage("schedule.event.description") + " " + attendee.getDisplayName());

        return calendar.events().insert(calendarId, event).execute();
    }

    // TODO: 10.08.2016 сделать метод для поиска покупок по клиенту   task1
    public Map<Long, Long> getPurchaseByClient(Long clientId) {
        List<Tuple> tuples = initPurchaseQuery(clientId).fetch();

        Map<Long, Long> payments = new HashMap<>(tuples.size());
        for (Tuple tuple : tuples) {
            payments.put(
                    tuple.get(payment.id),
                    tuple.get(payment.paymentAmount)
            );
        }
        return payments;
    }

    private JPAQuery<Tuple> initPurchaseQuery(Long clientId) {
        return getQuery()
                .select(payment.id, payment.paymentAmount, purchase.discount)
                .from(purchase)
                .leftJoin(purchase.payments, payment)
                .where(purchase.client.id.eq(clientId));
    }

    // TODO: 10.08.2016 сделать метод для поиска платежей по покупке    task2


}
