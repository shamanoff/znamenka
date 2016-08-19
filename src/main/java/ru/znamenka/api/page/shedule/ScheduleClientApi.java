package ru.znamenka.api.page.shedule;


import lombok.Builder;
import lombok.Getter;
import ru.znamenka.api.BaseApi;

@Builder
public class ScheduleClientApi implements BaseApi {

    @Getter
    private final Long id;
    @Getter
    private final String name;



}
