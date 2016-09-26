package ru.znamenka.jpa.repository.domain;

import org.springframework.data.repository.CrudRepository;
import ru.znamenka.jpa.model.ActionLog;

public interface ActionLogRepository extends CrudRepository<ActionLog, Long> {
}
