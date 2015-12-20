package net.iryndin.lunchapp.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * H2 database configuration.
 * Define address of H2 web concole.
 */
@Configuration
public class H2WebConfig {

    static final String PATH_TO_H2_CONSOLE = "/h2console/*";

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings(PATH_TO_H2_CONSOLE);
        return registrationBean;
    }
}
