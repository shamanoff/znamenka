package ru.click.crm.represent.converter.page.sale;


import com.querydsl.core.Tuple;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.click.crm.represent.page.sale.ClientDebtApi;

/**
 * <p>
 * <p>
 * Создан 11.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class ClientPaymentApiConverter implements Converter<Tuple, ClientDebtApi> {

    @Override
    public ClientDebtApi convert(Tuple tuple) {
        return ClientDebtApi
                .builder()
                .purchaseId(tuple.get(0, Long.class))
                .productName(tuple.get(1, String.class))
                .paid(tuple.get(2, Number.class).doubleValue())
                .remain(tuple.get(3, Number.class).doubleValue())
                .build();
    }
}
