package ru.znamenka.security;

import com.google.api.services.calendar.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.verify;


/**
 * <p>
 * <p>
 * Создан 09.09.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@RunWith(MockitoJUnitRunner.class)
public class GoogleAuthorizerTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private ApplicationContext mockCtx;

    @Test
    public void getCalendar() throws Exception {
        GoogleAuthorizer authorizer = new GoogleAuthorizer(mockCtx);
        Calendar calendar = authorizer.getCalendar();
        verify(mockCtx).getApplicationName();
    }

}