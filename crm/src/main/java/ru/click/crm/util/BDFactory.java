package ru.click.crm.util;


import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

/**
 * <p>
 * <p>
 * Создан 04.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class BDFactory {

    private BDFactory() {}

    public static BigDecimal bd(int value) {
        return scale(new BigDecimal(value));
    }
    public static BigDecimal bd(double value) {
        return scale(new BigDecimal(value));
    }
    public static BigDecimal bd(long value) {
        return scale(new BigDecimal(value));
    }

    private static BigDecimal scale(BigDecimal bd) {
        return bd.setScale(2, ROUND_HALF_EVEN);
    }
}
