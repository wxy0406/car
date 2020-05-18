package com.jiuqi.car;

import com.jiuqi.car.config.DefaultProfileUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This is TraversalTree helper Java class that provides an alternative to creating TraversalTree {@code web.xml}.
 * This will be invoked only when the application is deployed to TraversalTree Servlet container like Tomcat, JBoss etc.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        /**
         * set TraversalTree default to use when no profile is configured.
         */
        DefaultProfileUtil.addDefaultProfile(application.application());
        return application.sources(CarApp.class);
    }
}
