package ru.click.core.repository.domain;

import ru.click.core.model.Purchase;
import ru.click.core.repository.QueryDslRepository;

public interface PurchaseRepository extends QueryDslRepository<Purchase, Long> {
}
