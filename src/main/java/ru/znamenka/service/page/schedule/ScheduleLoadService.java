package ru.znamenka.service.page.schedule;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.znamenka.api.CalendarEvent;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.znamenka.util.Utils.*;

@Service
@Slf4j
public class ScheduleLoadService {

    @Autowired
    private Calendar calendar;

    @Value("${calendar.id:primary}")
    private String calendarId;

    public List<CalendarEvent> loadEventsBusy(Date startDate, Date endDate) {
        List<CalendarEvent> events = loadEvents(startDate, endDate);
        return events.stream().map(e -> e.setTitle("Занято")).collect(Collectors.toList());
    }

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

}
