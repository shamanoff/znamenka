package ru.click.crm.controller.page;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.click.core.model.CalendarEvent;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * <p>
 * Создан 23.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
@Slf4j
public class CalendarController {

    private final SimpMessageSendingOperations mesTemplate;

    @Autowired
    public CalendarController(SimpMessageSendingOperations mesTemplate) {
        notNull(mesTemplate);
        this.mesTemplate = mesTemplate;
    }

    @GetMapping("/events")
    public String calendarIndex() {
        return "calendar";
    }

    @MessageMapping("/calendar/add")
    public void event(CalendarEvent event) {
        log.info(event.toString());
    }

}
