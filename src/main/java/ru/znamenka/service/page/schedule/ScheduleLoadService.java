package ru.znamenka.service.page.schedule;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.*;
import ru.znamenka.jpa.repository.EntityRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleLoadService {

    @Autowired
    private Calendar calendar;

    @Autowired
    @Qualifier("facadeRepository")
    private EntityRepository repo;


    public List<Training> test(Date date) throws IOException {
        List<Event> eventList = getEventsByDate(date);

        List<Training> trainings = new ArrayList<>();
        for (Event event : eventList) {
            DateTime start = event.getStart().getDateTime();
            ZonedDateTime dt = ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(start.getValue()), ZoneOffset.ofHours(start.getTimeZoneShift() / 60)
            );
            String trainerName = event.getSummary();
            Trainer trainer = repo.findOne(Trainer.class, QTrainer.trainer.name.eq(trainerName));
            if (event.getAttendees().isEmpty()) {
                throw new RuntimeException();
            }
            String clientEmail = event.getAttendees().stream().findFirst().get().getEmail();
            Client client = repo.findOne(Client.class, QClient.client.email.eq(clientEmail));
            Training training = new Training();
            training.setClient(client);
            training.setTrainer(trainer);
            training = repo.save(Training.class, training);
            trainings.add(training);
        }
        return trainings;
    }

    private List<Event> getEventsByDate(Date date) {
        return getEvents()
                .getItems()
                .stream()
                .filter(event -> {
                    DateTime dateTime = event.getStart().getDateTime();
                    if (dateTime == null) return false;
                    return dateTime.getValue() == date.getTime();
                })
                .collect(Collectors.toList());
    }


    private Events getEvents() {
        try {
            return calendar
                    .events()
                    .list("4jto0age6tsrrkuhveervcj0sk@group.calendar.google.com")
                    .setMaxResults(10)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
