package ru.znamenka.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.google.api.services.calendar.CalendarScopes.CALENDAR;
import static java.util.Collections.singleton;

@Component
public class GoogleAuthorizer {

    @Autowired
    private ApplicationContext ctx;

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();


    public Calendar getCalendar() throws IOException {
        Resource resource = new ClassPathResource("Znamenka2-f7124695d156.json");
        String appName = ctx.getApplicationName();

        GoogleCredential credential = GoogleCredential.fromStream(resource.getInputStream()).createScoped(singleton(CALENDAR));

        return new Calendar
                .Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(appName).build();

    }
}
