package ru.znamenka.util;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * Создан 17.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public enum DutyColor {

    palegreen("#98FB98", "#FFFFFF"),
    lightseagreen("#20B2AA", "#FFFFFF"),
    skyblue("#87CEEB", "#FFFFFF");

    private String rgbBack;

    private String rgbText;

    private static Map<Integer, DutyColor> colorMap;

    static {
        Map<Integer, DutyColor> map = new HashMap<>(3);
        map.put(1, skyblue);
        map.put(2, palegreen);
        map.put(3, lightseagreen);
        colorMap = unmodifiableMap(map);
    }

    DutyColor(String rgbBack, String rgbText) {
        this.rgbBack = rgbBack;
        this.rgbText = rgbText;
    }

    public String background() {
        return rgbBack.toLowerCase();
    }

    public String text() {
        return rgbText;
    }

    public static DutyColor get(Long typeId) {
        notNull(typeId, "Type of duty not must be null");
        return colorMap.get(typeId.intValue());
    }


}
