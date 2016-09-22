package ru.znamenka.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent {

    private String title;

    private Timestamp start;

    private Timestamp end;

}
