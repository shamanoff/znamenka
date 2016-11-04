package ru.znamenka.service.subsystem.training;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.model.DutySchedule;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.model.CalendarEvent;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.jpa.util.DutyColor;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;
import static ru.znamenka.jpa.model.QDutySchedule.dutySchedule;
import static ru.znamenka.jpa.model.QTraining.training;
import static ru.znamenka.jpa.util.Utils.*;

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
    public EventService(
            Calendar calendar,
            EntityRepository repo
    ) {
        notNull(calendar);
        notNull(repo);
        this.calendar = calendar;
        this.repo = repo;
    }

    @Override
    public List<CalendarEvent> loadEventsBusy(Date startDate, Date endDate) {
        List<CalendarEvent> events = loadEvents(startDate, endDate);
        return events.stream().map(e -> e.title("Занято")).collect(toList());
    }

    @Override
    public List<CalendarEvent> loadEvents(Date startDate, Date endDate) {
        List<Training> trainings = repo.findAll(Training.class, training.start.between(fromDate(startDate), fromDate(endDate)));
        List<CalendarEvent> calendarEvents = new ArrayList<>(trainings.size());
        for (Training training : trainings) {
            val start = training.getStart();
            // TODO: 05.10.2016 обязательно переделать, а то в разных местах прибавляется
            val end = start.plus(30L, MINUTES);
            Client client = training.getClient();
            val calendarEvent = CalendarEvent
                    .createEvent()
                    .id(training.getId())
                    .title(client.getFullName())
                    .start(start)
                    .end(end);

            if (training.getPassForAuto()) {
                calendarEvent.color("#48D1CC", "#FFFFFF");
            }
            calendarEvents.add(calendarEvent);
        }
        return calendarEvents;
    }

    @Override
    public List<CalendarEvent> loadDutyEvents(Date startDate, Date endDate) {
        List<DutySchedule> duties = repo.findAll(DutySchedule.class, dutySchedule.plannedStart.between(fromDate(startDate), fromDate(endDate)));
        List<CalendarEvent> calendarEvents = new ArrayList<>(duties.size());
        for (DutySchedule duty : duties) {
            val color = DutyColor.get(duty.getPlanTypeId());
            val calendarEvent = CalendarEvent
                    .createEvent()
                    .id(duty.getId())
                    .title(duty.getTrainer().getName())
                    .start(duty.getPlannedStart())
                    .end(duty.getPlannedEnd())
                    .color(color);
            calendarEvents.add(calendarEvent);
        }
        return calendarEvents;
    }

    /**
     * Постит сохраненную тренировку в гугль календарь
     *
     * @param training тренеровка
     */
    @Deprecated
    @Override
    public void postToCalendar(TrainingApi training) {
        LocalDateTime start = training.getStart();
        LocalDateTime end = start.plus(30L, MINUTES);
        Client client = repo.findOne(Client.class, training.getClientId());
        String clientName = client.getName() + " " + client.getSurname();
        String clientEmail = client.getEmail();
        Event event = createEvent(start, end, clientName, clientEmail);

        try {
            calendar.events().insert(calendarId, event).execute();
            CalendarEvent busyEvent = CalendarEvent
                    .createEvent()
                    .id(training.getId())
                    .title("Занято")
                    .start(start)
                    .end(end);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Deprecated
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

    @Deprecated
    private Event createEvent(LocalDateTime start, LocalDateTime end, String clientName, String clientEmail) {
        Event event = new Event();

        event.setCreated(googleTime(start));

        EventDateTime startEvent = new EventDateTime().setDateTime(googleTime(start)).setTimeZone("Europe/Moscow");
        event.setStart(startEvent);

        EventDateTime endEvent = new EventDateTime().setDateTime(googleTime(end)).setTimeZone("Europe/Moscow");
        event.setEnd(endEvent);

        event.setSummary(clientName);
        EventAttendee attendee = new EventAttendee();
        attendee.setEmail(clientEmail);
        attendee.setDisplayName(clientName);
        event.setAttendees(singletonList(attendee));
        event.setDescription("Запланирована тренировка для клиента: " + attendee.getDisplayName());
        return event;
    }
}
