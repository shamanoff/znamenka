package ru.znamenka.represent.domain;

import lombok.Getter;
import lombok.Setter;
import ru.znamenka.represent.DomainApi;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class PaymentApi implements DomainApi {

    @Getter
    @NotNull
    @Setter
    private Long amount;

    @Getter
    @NotNull
    @Setter
    private Long purchaseId;

    @Getter @Setter
    private Timestamp paymentDate;
}
