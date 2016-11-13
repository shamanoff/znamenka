package ru.click.core.repository.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.click.core.model.Training;
import ru.click.core.repository.QueryDslRepository;

/**
 * <p>
 * <p>
 * Создан 01.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface TrainingRepository extends QueryDslRepository<Training, Long> {

    @Override
    @EntityGraph("Training.Graph")
    Training findOne(Long id);

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    <S extends Training> S saveAndFlush(S s);
}
