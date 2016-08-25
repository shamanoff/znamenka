package ru.znamenka.api.converter.page.sale;

import com.querydsl.core.Tuple;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.znamenka.api.page.sale.PaymentsApi;

/**
 * <p>
 *
 * Создан 11.08.2016
 * <p>

 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class PaymentsApiConverter implements Converter<Tuple,PaymentsApi> {


    @Override
    public PaymentsApi convert(Tuple tuple) {
        return PaymentsApi
                .builder()
                .purchaseId(tuple.get(0, Long.class))
                .productName(tuple.get(1, String.class))
                .paid(tuple.get(2, Number.class).doubleValue())
                .remain(tuple.get(3, Number.class).doubleValue())
                .build();
    }
}
