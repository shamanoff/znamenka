package ru.znamenka.util;

import com.google.api.client.util.DateTime;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

/**
 * <p>
 *      Util класс для всякого разного
 * <p>
 * Создан 22.06.2016
 * <p>
 * @author Евгений Уткин (Eugene Utkin)
 *
 */
@NoArgsConstructor(access = PRIVATE)
public final class Utils {

    /**
     * Генерирует последовательность целых чисел, как в Python
     * @param from начальное число
     * @param to конечное включительно
     * @return список последовательных чисел
     */
    public static List<Integer> range(int from, int to) {
        return IntStream.range(from, to + 1).boxed().collect(toList());
    }

    /**
     * Конвертирует Iterable в список
     * @param iterable конвертируемой объект
     * @param <T> тип
     * @return список
     */
    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        if (iterable == null) return null;
        List<T> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

    /**
     * Объединяет несколько коллекций в один список
     * @param collection коллекция коллекций
     * @param <T> тип
     * @return список со всеми элементами коллекций
     */
    public static <T> List<T> joinLists(Collection<Collection<T>> collection) {
        return collection.stream().map(Collection::stream).reduce(Stream::concat).orElseThrow(RuntimeException::new).collect(Collectors.toList());
    }

    /**
     * Объединяет несколько коллекций в один список
     * @param collections коллекции
     * @param <T> тип
     * @return список со всеми элементами коллекций
     */
    public static <T> List<T> joinLists(Collection<T>... collections) {
        return joinLists(asList(collections));
    }

    /**
     * todo добавить проверку на null
     * @param param
     * @return
     */
    public static String join(String... param) {
        if (param.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (String s : param) {
            sb.append(s).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static DateTime googleTime(ZonedDateTime time) {
        return new DateTime(time.toInstant().toEpochMilli(), time.getOffset().getTotalSeconds() / 60);
    }

    public static DateTime googleTime(Timestamp timestamp) {
        ZonedDateTime time = ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneOffset.UTC);
        return googleTime(time);
    }

    public static ZonedDateTime javaTime(DateTime dateTime) {
        return ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(dateTime.getValue()), ZoneOffset.ofHours(dateTime.getTimeZoneShift() / 60)
        );
    }

    public static Timestamp fromLocalDate(LocalDate date) {
        return Timestamp.valueOf(LocalDateTime.of(date, LocalTime.MIN));
    }

}
