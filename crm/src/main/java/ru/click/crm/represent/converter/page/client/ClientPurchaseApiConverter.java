package ru.click.crm.represent.converter.page.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.click.crm.represent.converter.domain.PaymentApiConverter;
import ru.click.crm.represent.domain.PaymentApi;
import ru.click.crm.represent.page.client.ClientPurchaseApi;
import ru.click.crm.util.BDFactory;
import ru.click.core.represent.converter.ApiConverter;
import ru.click.core.model.Payment;
import ru.click.core.model.Purchase;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static ru.click.crm.util.BDFactory.bd;

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
                .priceDisc(priceWithDiscount(source.getProduct().getPrice(), source.getDiscount() != null ? source.getDiscount().getDiscountAmount() : null))
                .discountAmount(source.getDiscount() != null ? String.valueOf(source.getDiscount().getDiscountAmount()) : " - ")
                .trainerName(source.getTrainer().getName())
                .purchaseDate(source.getPurchaseDate().toLocalDate())
                .payments(payments(source.getPayments()))
                .build();
    }

    private String priceWithDiscount(Double price, Integer discount) {
        if (discount == null) return price.toString();
        BigDecimal bdPrice = BDFactory.bd(price);
        BigDecimal bdDisc = BDFactory.bd((double) (100 - discount) /100);
        return bdPrice.multiply(bdDisc).doubleValue() + "";
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
