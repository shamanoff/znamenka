package ru.znamenka.api.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.DiscountApi;
import ru.znamenka.jpa.model.Discount;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class DiscountApiConverter implements ApiConverter<Discount, DiscountApi> {
    @Override
    public Class<DiscountApi> getApiType() {
        return DiscountApi.class;
    }

    @Override
    public Class<Discount> getEntityType() {
        return Discount.class;
    }

    // TODO: 22.08.2016
    @Override
    public Discount convertTo(DiscountApi source) {
       throw new UnsupportedOperationException();
    }

    @Override
    public DiscountApi convert(Discount source) {
        return DiscountApi
                .builder()
                .id(source.getId())
                .amount(source.getDiscountAmount())
                .build();
    }
}
