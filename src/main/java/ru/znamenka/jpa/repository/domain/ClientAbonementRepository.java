package ru.znamenka.jpa.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.znamenka.jpa.model.ClientAbonement;

public interface ClientAbonementRepository extends JpaRepository<ClientAbonement, Long> {
}
