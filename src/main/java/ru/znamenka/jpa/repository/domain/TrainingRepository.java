package ru.znamenka.jpa.repository.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.jpa.repository.QueryDslRepository;

import java.util.List;

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
    List<Training> findAll();

    @Override
    @EntityGraph("Training.Graph")
    Training findOne(Long id);

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    <S extends Training> S saveAndFlush(S s);
}
