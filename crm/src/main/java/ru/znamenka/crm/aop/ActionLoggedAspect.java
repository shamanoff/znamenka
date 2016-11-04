package ru.znamenka.crm.aop;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.znamenka.crm.annotation.ActionLogged;
import ru.znamenka.crm.service.logging.LoggingService;
import ru.znamenka.jpa.model.ActionLog;
import ru.znamenka.jpa.model.User;

import static org.springframework.util.Assert.notNull;

@Component
@Aspect
@Slf4j
public class ActionLoggedAspect {

    /**
     * Сервис для логгирования действия
     */
    private final LoggingService<ActionLog> loggingService;

    /**
     * Констуктор для внедрения сервиса логгирования
     *
     * @param loggingService сервис логгирования
     */
    @Autowired
    public ActionLoggedAspect(@Qualifier("actionLogging") LoggingService<ActionLog> loggingService) {
        notNull(loggingService);
        this.loggingService = loggingService;
    }



    /**
     * Аспект для логгирования действия, совершаемого аннотацируемым методом.
     *
     * @param pjp точка входа в метод
     * @return возвращает то же значение, что и аннотируемый метод
     * @throws Throwable исключение, бросаемое аннотируемым методом
     */
    @Around("@annotation(ru.znamenka.crm.annotation.ActionLogged)")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getSignature() instanceof MethodSignature) {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            val method = signature.getMethod();

            ActionLogged annotation = method.getAnnotation(ActionLogged.class);
            String action = annotation.action();
            String success = annotation.successText();
            String failed = annotation.failedText();

            Object[] parameters = pjp.getArgs();
            if (parameters.length > 0) {
                Object param = parameters[0];
                action = action + ": " + param;
            }

            val user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            try {
                Object proceed = pjp.proceed();
                val actionLog = ActionLog.of(user, action, true, success);
                loggingService.log(actionLog);
                return proceed;
            } catch (Exception e) {
                val actionLog = ActionLog.of(user, action, false, failed);
                loggingService.log(actionLog);
                throw e;
            }
        }
        return pjp.proceed();
    }

}
