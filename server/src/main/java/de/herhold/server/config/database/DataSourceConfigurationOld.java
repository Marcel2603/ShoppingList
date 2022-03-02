package de.herhold.server.config.database;

//@Data
//@NoArgsConstructor
//@Configuration
//@ConfigurationProperties(prefix = "spring.r2dbc")
public class DataSourceConfigurationOld {
    private String url;
    private String driverClassName;
    private String username;
    private String password;
}
