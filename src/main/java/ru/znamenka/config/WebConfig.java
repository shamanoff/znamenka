package ru.znamenka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import ru.znamenka.util.locale.ExtMessageSource;

import java.nio.charset.Charset;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * <p>
 * Web конфигурация
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Configuration
public class WebConfig extends SpringDataWebConfiguration {

    /**
     * Расположение ресурсов
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};


    @Autowired
    private ExtMessageSource source;

    /**
     * {@inheritDoc}
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        HandlerMethodArgumentResolver pageableResolverOne = pageableResolver();
        argumentResolvers.add(pageableResolverOne);
    }


    /**
     * {@inheritDoc}
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    /**
     * {@inheritDoc}
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }


    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Bean
    @Override
    public PageableHandlerMethodArgumentResolver pageableResolver() {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver(sortResolver());
        pageableResolver.setOneIndexedParameters(true);

        return pageableResolver;
    }

    /**
     * Включает использование локали через заголовок запроса
     *
     * @return localeResolver
     * @see AcceptHeaderLocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(source);
        return bean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        MediaType mediaType = new MediaType("application","x-www-form-urlencoded", Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(singletonList(mediaType));
        converters.add(converter);
        super.configureMessageConverters(converters);
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

}
