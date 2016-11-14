package ru.click.cabinet.repository;

import ru.click.core.model.Training;

import java.util.List;

/**
 * <p>
 * Создан 14.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface TrainingManager {

    List<Training> trainings(Long clientId);
}
