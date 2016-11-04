package ru.znamenka.represent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.znamenka.represent.DomainApi;
import ru.znamenka.represent.UpdatableApi;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter @Accessors(chain = true)
public class DutyApi implements DomainApi, UpdatableApi<Long> {

    private Long Id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime plannedStart;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime plannedEnd;


    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime factStart;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime factEnd;

    @NotNull
    private Long planTypeId;

    private String planTypeName;

    private Long trainerId;
}
