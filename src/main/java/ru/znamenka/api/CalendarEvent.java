package ru.znamenka.api;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * <p>
 * <p>
 * Создан 22.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter
@Setter
public class CalendarEvent {

    public CalendarEvent() {
    }

    public CalendarEvent(String title, Timestamp start, Timestamp end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    private String title;

    private Timestamp start;

    private Timestamp end;

}
