package ru.znamenka.represent.converter.page.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.znamenka.represent.converter.ApiConverter;
import ru.znamenka.represent.converter.domain.PaymentApiConverter;
import ru.znamenka.represent.domain.PaymentApi;
import ru.znamenka.represent.page.client.ClientPurchaseApi;
import ru.znamenka.jpa.model.Payment;
import ru.znamenka.jpa.model.Purchase;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * <p>
 * <p>
 * Создан 13.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class ClientPurchaseApiConverter implements ApiConverter<Purchase, ClientPurchaseApi> {

    @Autowired
    private PaymentApiConverter converter;

    @Override
    public Class<Purchase> getEntityType() {
        return Purchase.class;
    }

    @Override
    public Class<ClientPurchaseApi> getApiType() {
        return ClientPurchaseApi.class;
    }

    @Override
    public Purchase convertTo(ClientPurchaseApi source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientPurchaseApi convert(Purchase source) {
        return ClientPurchaseApi
                .builder()
                .productName(source.getProduct().getProductName())
                .price(source.getProduct().getPrice().toString())
                .paid(paid(source.getPayments()))
                .discountAmount(source.getDiscount() != null ? String.valueOf(source.getDiscount().getDiscountAmount()) : " - ")
                .trainerName(source.getTrainer().getName())
                .purchaseDate(Timestamp.valueOf(source.getPurchaseDate().toLocalDate().atStartOfDay()))
                .payments(payments(source.getPayments()))
                .build();
    }

    private String paid(List<Payment> payments) {
        if (payments == null) return " - ";
        return payments
                .stream()
                .map(Payment::getPaymentAmount)
                .reduce((p1, p2) -> p1 + p2)
                .map(Object::toString)
                .orElse(" - ");
    }

    private List<PaymentApi> payments(List<Payment> payments) {
        if (payments == null) return emptyList();
        return payments
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
