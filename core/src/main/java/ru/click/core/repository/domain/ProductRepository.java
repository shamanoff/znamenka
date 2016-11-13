package ru.click.core.repository.domain;

import ru.click.core.repository.QueryDslRepository;
import ru.click.core.model.Product;

public interface ProductRepository extends QueryDslRepository<Product, Long> {
}
