package ru.znamenka.represent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.znamenka.represent.DomainApi;
import ru.znamenka.represent.UpdatableApi;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>Представление для бизнес-модели Тренировка
 * <p>
 * Создан 01.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter @Setter @Accessors(chain = true)
public class TrainingApi implements DomainApi, UpdatableApi<Long> {

    public static TrainingApi empty() {
        return new TrainingApi();
    }
    
    private Long id;

    @NotNull
    private Long trainerId;

    private String trainerName;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime start;

    @NotNull
    private Long clientId;

    private String clientName;

    private Long purchaseId;

    private Long statusId;

    private String statusName;

    private String comment;

    private Boolean passForAuto;

    private String carNumber;

    private String abonement;
}
