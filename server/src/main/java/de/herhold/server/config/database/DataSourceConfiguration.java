package de.herhold.server.config.database;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Configuration
@ToString
@ConfigurationProperties(prefix = "spring.r2dbc")
public class DataSourceConfiguration {
    private String url;
    private String driverClassName;
    private String username;
    private String password;
}
