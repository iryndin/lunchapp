package net.iryndin.lunchapp.config;

import org.junit.Test;
import org.springframework.boot.context.embedded.ServletRegistrationBean;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for H2WebConfig
 */
public class H2WebConfigTest {

    @Test
    public void testH2servletRegistration() {
        H2WebConfig cfg = new H2WebConfig();
        ServletRegistrationBean reg = cfg.h2servletRegistration();
        assertTrue(reg.getUrlMappings().contains(cfg.PATH_TO_H2_CONSOLE));
    }
}
