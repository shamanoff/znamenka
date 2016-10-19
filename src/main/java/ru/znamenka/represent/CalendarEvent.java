package ru.znamenka.represent;

import lombok.Getter;
import ru.znamenka.util.DutyColor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

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

    private Long id;

    private String title;

    private Timestamp start;

    private Timestamp end;

    private String backgroundColor;

    private String textColor;

    public static CalendarEvent createEvent() {
        return new CalendarEvent();
    }

    public CalendarEvent id(Long id) {
        this.id = id;
        return this;
    }

    public CalendarEvent title(String title) {
        this.title = title;
        return this;
    }

    public CalendarEvent start(LocalDateTime start) {
        this.start = Timestamp.from(start.toInstant(UTC));
        return this;
    }

    public CalendarEvent end(LocalDateTime end) {
        this.end = Timestamp.from(end.toInstant(UTC));
        return this;
    }

    public CalendarEvent color(DutyColor color) {
        this.backgroundColor = color.background();
        this.textColor = color.text();
        return this;
    }

    public CalendarEvent color(String background, String text) {
        this.backgroundColor = background;
        this.textColor = text;
        return this;
    }
}
