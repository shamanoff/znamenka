package ru.click.core.repository.domain;

import ru.click.core.model.Payment;
import ru.click.core.repository.QueryDslRepository;

public interface PaymentRepository extends QueryDslRepository<Payment, Long> {
}
