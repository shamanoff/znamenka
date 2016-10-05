package ru.znamenka.represent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * <p>
 * Создан 22.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter @Setter @Accessors(chain = true) @NoArgsConstructor
public class CalendarEvent {
    
    private Long id;

    private String title;
    
    private Timestamp start;
    
    private Timestamp end;

    public CalendarEvent(Long id, String title, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.title = title;
        this.start = Timestamp.valueOf(start);
        this.end = Timestamp.valueOf(end);
    }
}
