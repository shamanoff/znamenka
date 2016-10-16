package ru.znamenka.jpa.repository.domain;

import ru.znamenka.jpa.model.DutySchedule;
import ru.znamenka.jpa.repository.QueryDslRepository;

public interface DutyRepository extends QueryDslRepository<DutySchedule, Long> {
}
