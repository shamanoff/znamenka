package ru.znamenka.represent.page.endofday;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Создан 18.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter @Setter
public class Report {

    private List<Status> statuses;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime factStart;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime factEnd;

    private Long dutyId;

}
