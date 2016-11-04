package ru.znamenka.jpa.repository.domain;

import ru.znamenka.jpa.repository.QueryDslRepository;
import ru.znamenka.jpa.model.Product;

public interface ProductRepository extends QueryDslRepository<Product, Long> {
}
