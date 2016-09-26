package ru.znamenka.represent;

import lombok.Getter;

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

    public CalendarEvent setTitle(String title) {
        this.title = title;
        return this;
    }

    public CalendarEvent setStart(Timestamp start) {
        this.start = start;
        return this;
    }

    public CalendarEvent setEnd(Timestamp end) {
        this.end = end;
        return this;
    }
}
