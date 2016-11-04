package ru.znamenka.crm.util.locale;


import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * <p>
 *     Расширяет класс {@link ResourceBundleMessageSource} и реализует
 *     интерфейс {@link ExtMessageSource}
 * <p>
 * Создан 27.06.2016
 * <p>
 * @author Евгений Уткин (Eugene Utkin)
 */
public class ExtResourceBundleSource extends ResourceBundleMessageSource implements ExtMessageSource {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String messageCode) {
        return super.getMessage(new DefaultMessageSourceResolvable(messageCode), LocaleContextHolder.getLocale());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String messageCode, String defaultCode) {
        try {
            return super.getMessage(new DefaultMessageSourceResolvable(new String[]{messageCode}), LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return super.getMessage(new DefaultMessageSourceResolvable(new String[]{defaultCode}), LocaleContextHolder.getLocale());
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String messageCode, Object[] args) {
        return super.getMessage(new DefaultMessageSourceResolvable(new String[] {messageCode}, args), LocaleContextHolder.getLocale());
    }
}
