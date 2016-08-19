package ru.znamenka.api.converter.page.sale;

import com.querydsl.core.Tuple;
import org.springframework.core.convert.converter.Converter;
import ru.znamenka.api.page.sale.PaymentsApi;

/**
 * Created by Сережа on 11.08.2016.
 */
public class PaymentsApiConverter implements Converter<Tuple,PaymentsApi> {


    @Override
    public PaymentsApi convert(Tuple tuple) {
        return null;
    }
}
