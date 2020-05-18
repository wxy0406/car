package com.jiuqi.car.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Car.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for TraversalTree good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
