package ru.click.core.converter;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * <p>
 * Создан 19.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(Boolean attribute) {
        return attribute == null ? false : attribute;
    }

    @Override
    public Boolean convertToEntityAttribute(Boolean dbData) {
        return dbData == null ? false : dbData;
    }
}
