package ru.znamenka.service.page.schedule;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.represent.domain.TrainingApi;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.springframework.util.Assert.notNull;
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
public class SubscriptionPageService {

    /**
     * Google календарь
     */
    private final Calendar calendar;

    /**
     * Репозиторий бизнес-моделей
     */
    private final EntityRepository repo;

    @Value("${calendar.id:primary}")
    private String calendarId;

    /**
     * Конструктор
     *
     * @param repo     репозиторий бизнес-моделей
     * @param calendar google календарь
     */
    @Autowired
    public SubscriptionPageService(@Qualifier("facadeRepository") EntityRepository repo,
                                   Calendar calendar
    ) {
        notNull(repo);
        notNull(calendar);
        this.repo = repo;
        this.calendar = calendar;
    }



    // // TODO: 11.08.2016 если нет таких клиентов и тренеров

    /**
     * Постит сохраненную тренировку в гугль календарь
     * @param training тренеровка
     * @return событие в гугль календаре
     * @throws IOException если возникли проблемы с отправкой запроса
     */
    public Event postToCalendar(TrainingApi training) {
        Event event = new Event();

        LocalDateTime start = training.getStart().toLocalDateTime();
        event.setCreated(googleTime(start));
        EventDateTime startEvent = new EventDateTime().setDateTime(googleTime(start)).setTimeZone("Europe/Moscow");
        event.setStart(startEvent);

        LocalDateTime end = training.getEnd().toLocalDateTime();
        EventDateTime endEvent = new EventDateTime().setDateTime(googleTime(end)).setTimeZone("Europe/Moscow");
        event.setEnd(endEvent);

        Client client = repo.findOne(Client.class, training.getClientId());
        event.setSummary(client.getName() + " " + client.getSurname());
        EventAttendee attendee = new EventAttendee();
        attendee.setEmail(client.getEmail());
        attendee.setDisplayName(client.getName() + " " + client.getSurname());
        event.setAttendees(singletonList(attendee));
        event.setDescription("Запланирована тренировка для клиента: " + attendee.getDisplayName());

        try {
            return calendar.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
