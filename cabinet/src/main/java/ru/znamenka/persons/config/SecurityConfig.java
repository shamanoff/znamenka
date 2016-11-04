package ru.znamenka.persons.config;

import lombok.val;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;
import ru.znamenka.persons.config.oauth2.FacebookConfig;
import ru.znamenka.persons.config.oauth2.GoogleConfig;
import ru.znamenka.persons.config.oauth2.util.OAuth2Provider;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Import({GoogleConfig.class, FacebookConfig.class})
@EnableOAuth2Client
public class SecurityConfig {

    @Autowired
    private ListableBeanFactory factory;

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }


    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(ssoFilters());
        return filter;
    }

    @Bean
    public List<Filter> filters() {
        return ssoFilters();
    }

    private List<Filter> ssoFilters() {
        Collection<OAuth2Provider> oauth2Providers = factory.getBeansOfType(OAuth2Provider.class).values();
        return oauth2Providers
                .stream()
                .map(p -> getFilter(p.getClient(), p.getResource(), p.getLogin()))
                .collect(Collectors.toList());
    }

    private OAuth2ClientAuthenticationProcessingFilter getFilter(OAuth2ProtectedResourceDetails details, ResourceServerProperties props, String loginUrl) {
        val filter = new OAuth2ClientAuthenticationProcessingFilter(loginUrl);
        val googleTemplate = new OAuth2RestTemplate(details);
        filter.setRestTemplate(googleTemplate);
        val tokenServices = new UserInfoTokenServices(props.getUserInfoUri(), details.getClientId());
        filter.setTokenServices(tokenServices);
        return filter;
    }

}
