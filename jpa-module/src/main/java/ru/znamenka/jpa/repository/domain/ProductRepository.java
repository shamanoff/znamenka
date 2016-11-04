package ru.znamenka.jpa.repository.domain;

import ru.znamenka.jpa.model.Product;
import ru.znamenka.jpa.repository.QueryDslRepository;

public interface ProductRepository extends QueryDslRepository<Product, Long> {
}
