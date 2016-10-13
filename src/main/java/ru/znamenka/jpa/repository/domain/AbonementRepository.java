package ru.znamenka.jpa.repository.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.znamenka.jpa.model.Abonement;

public interface AbonementRepository extends JpaRepository<Abonement, Long> {
}
