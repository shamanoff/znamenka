package ru.znamenka.service.schedule;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ScheduleLoadService {

    @Autowired
    private Calendar calendar;


    public void test() throws IOException {
        Events events = calendar.events().list("4jto0age6tsrrkuhveervcj0sk@group.calendar.google.com")
                .setMaxResults(10)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> eventList = events.getItems();
        for (Event event : eventList) {
            DateTime start = event.getStart().getDateTime();
            log.info(event.getDescription() + start.toString());
        }
    }

}
