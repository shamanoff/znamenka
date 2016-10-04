package ru.znamenka.service.subsystem.training;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.represent.CalendarEvent;
import ru.znamenka.represent.domain.TrainingApi;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;
import static ru.znamenka.util.Utils.googleDate;
import static ru.znamenka.util.Utils.googleTime;
import static ru.znamenka.util.Utils.javaTime;

/**
 * Создан 04.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
@Slf4j
public class EventService implements IEventService {

    /**
     * Google календарь
     */
    private final Calendar calendar;

    @Value("${calendar.id:primary}")
    private String calendarId;

    /**
     * Репозиторий бизнес-моделей
     */
    private final EntityRepository repo;

    @Autowired
    public EventService(Calendar calendar, EntityRepository repo) {
        notNull(calendar);
        notNull(repo);
        this.calendar = calendar;
        this.repo = repo;
    }

    @Override
    public List<CalendarEvent> loadEventsBusy(Date startDate, Date endDate) {
        List<CalendarEvent> events = loadEvents(startDate, endDate);
        return events.stream().map(e -> e.setTitle("Занято")).collect(toList());
    }

    @Override
    public List<CalendarEvent> loadEvents(Date startDate, Date endDate) {
        List<Event> eventList = getEvents(startDate, endDate).getItems();
        List<CalendarEvent> calendarEvents = new ArrayList<>(eventList.size());
        for (Event event : eventList) {
            DateTime start = event.getStart().getDateTime();
            Timestamp startEvent = Timestamp.valueOf(javaTime(start));
            DateTime end = event.getEnd().getDateTime();
            Timestamp endEvent = Timestamp.valueOf(javaTime(end));
            String summary = event.getSummary();
            CalendarEvent calendarEvent = new CalendarEvent(summary, startEvent, endEvent);
            calendarEvents.add(calendarEvent);
        }
        return calendarEvents;
    }

    private Events getEvents(Date startDate, Date endDate) {
        try {
            return calendar
                    .events()
                    .list(calendarId)
                    .setTimeMin(googleDate(startDate))
                    .setTimeMax(googleDate(endDate))
                    .execute();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Постит сохраненную тренировку в гугль календарь
     *
     * @param training тренеровка
     */
    @Override
    public void postToCalendar(TrainingApi training) {
        Event event = new Event();

        LocalDateTime start = training.getStart().toLocalDateTime();
        event.setCreated(googleTime(start));
        EventDateTime startEvent = new EventDateTime().setDateTime(googleTime(start)).setTimeZone("Europe/Moscow");
        event.setStart(startEvent);


        LocalDateTime end = start.plus(30L, MINUTES);
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
            calendar.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
