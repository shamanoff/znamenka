package ru.click.crm.represent.domain;


import lombok.Getter;
import lombok.Setter;
import ru.click.core.represent.DomainApi;

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
