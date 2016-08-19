package ru.znamenka.api.converter.page.schedule;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.page.shedule.ScheduleClientApi;
import ru.znamenka.jpa.model.Client;

import static java.util.Arrays.asList;

@Component
public class ScheduleClientApiConverter implements ApiConverter<Client, ScheduleClientApi> {

    @Override
    public Class<ScheduleClientApi> getApiType() {
        return ScheduleClientApi.class;
    }

    @Override
    public Class<Client> getEntityType() {
        return Client.class;
    }

    @Override
    public Client convertTo(ScheduleClientApi source) {
        return null;
    }

    @Override
    public ScheduleClientApi convert(Client client) {
        return ScheduleClientApi
                .builder()
                .id(client.getId())
                .name(StringUtils.join(asList(client.getName(),client.getSurname()) , " "))
                .build();
    }


}
