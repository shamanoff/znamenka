package ru.znamenka.jpa.repository.domain;

import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.repository.QueryDslRepository;

public interface PurchaseRepository extends QueryDslRepository<Purchase, Long> {
}
