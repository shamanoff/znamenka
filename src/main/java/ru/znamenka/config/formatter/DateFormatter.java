package ru.znamenka.config.formatter;

import org.springframework.format.Formatter;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (text == null || text.isEmpty()) return null;
        return Date.valueOf(LocalDate.parse(text, formatter));
    }

    @Override
    public String print(Date date, Locale locale) {
        return formatter.format(date.toLocalDate());
    }
}
