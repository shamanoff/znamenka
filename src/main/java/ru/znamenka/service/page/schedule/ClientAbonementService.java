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
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.model.Trainer;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.repository.QueryFactory;
import ru.znamenka.util.locale.ExtMessageSource;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.util.Collections.singletonList;
import static ru.znamenka.jpa.model.QProduct.product;
import static ru.znamenka.jpa.model.QPurchase.purchase;
import static ru.znamenka.util.Utils.googleTime;

import static ru.znamenka.jpa.model.QPayment.payment;

@Service
public class ClientAbonementService {

    @Autowired
    private QueryFactory factory;

    @Autowired
    private Calendar calendar;

    @Autowired
    @Qualifier("facadeRepository")
    private EntityRepository repo;

    @Autowired
    private ExtMessageSource message;

    @Value("${calendar.id:primary}")
    private String calendarId;

    public Map<Long, String> getAbonementByClient(Long clientId) {
        JPAQuery<Tuple> query = factory.getJpaQuery();
        query
                .select(purchase.id, product.productName)
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(purchase.client.id.eq(clientId).and(product.id.in(1,2,3)).and(product.expireDays.gt(0)));
        List<Tuple> tuples = query.fetch();

        Map<Long, String> abonements = new HashMap<>(tuples.size());
        for (Tuple tuple : tuples) {
            abonements.put(tuple.get(purchase.id), tuple.get(product.productName));
        }
       return abonements;
    }

    // // TODO: 11.08.2016 если нет таких клиентов и тренеров
    public Event postToCalendar(TrainingApi training) throws IOException {
        Event event = new Event();
        event.setCreated(googleTime(training.getStart()));

        EventDateTime start = new EventDateTime()
                .setDateTime(googleTime(training.getStart()))
                .setTimeZone("Europe/Moscow");
        event.setStart(start);

        LocalDateTime time = training.getStart().toLocalDateTime().plus(2, HOURS);
        EventDateTime end = new EventDateTime()
                .setDateTime(googleTime(Timestamp.valueOf(time)))
                .setTimeZone("Europe/Moscow");
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
//Кодил Сережа start
    public Map<Long, Long> getPurchaseByClient(Long clientId) {
        JPAQuery<Tuple> query = factory.getJpaQuery();
        query
                .select(payment.id, payment.paymentAmount,purchase.discount)
                .from(purchase)
                //.leftJoin(purchase.product, product)
                .leftJoin(purchase.payments , payment)
                .where(purchase.client.id.eq(clientId));
        List<Tuple> tuples = query.fetch();

        Map<Long, Long> payments = new HashMap<>(tuples.size());
        for (Tuple tuple : tuples) {
            payments.put(tuple.get(payment.id), tuple.get(payment.paymentAmount));
        }
        return payments;
    }
//Кодил Сережа end
    // TODO: 10.08.2016 сделать метод для поиска платежей по покупке    task2



}
