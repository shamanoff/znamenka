package ru.znamenka.jpa.repository.domain;

import ru.znamenka.jpa.model.Payment;
import ru.znamenka.jpa.repository.QueryDslRepository;

public interface PaymentRepository extends QueryDslRepository<Payment, Long> {
}
