package ru.znamenka.jpa.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.represent.CalendarEvent;
import ru.znamenka.util.AutowireHelper;

import javax.persistence.PostPersist;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * <p>
 * Создан 14.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class CreatedTrainingListener {

    private SimpMessageSendingOperations mesTemplate;

    @PostPersist
    private void postToCalendar(Training training) {
        AutowireHelper.autowire(this, this.mesTemplate);
        LocalDateTime start = training.getStart();
        LocalDateTime end = start.plus(30L, MINUTES);
        CalendarEvent busyEvent = CalendarEvent
                .createEvent()
                .id(training.getId())
                .title("Занято")
                .start(start)
                .end(end);
        mesTemplate.convertAndSend("/calendar/event", busyEvent);

    }

    @Autowired
    public void setMesTemplate(SimpMessageSendingOperations mesTemplate) {
        this.mesTemplate = mesTemplate;
    }
}
