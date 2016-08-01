package ru.znamenka.util;

import lombok.NoArgsConstructor;

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

}
