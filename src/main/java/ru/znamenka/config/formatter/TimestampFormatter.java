package ru.znamenka.config.formatter;

import org.springframework.format.Formatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimestampFormatter implements Formatter<Timestamp> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public Timestamp parse(String text, Locale locale) throws ParseException {
        LocalDateTime time = LocalDateTime.parse(text, formatter);
        return Timestamp.valueOf(time);
    }

    @Override
    public String print(Timestamp timestamp, Locale locale) {
        return formatter.format(timestamp.toLocalDateTime());
    }
}
