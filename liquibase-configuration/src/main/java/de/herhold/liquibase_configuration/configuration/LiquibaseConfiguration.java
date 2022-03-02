package de.herhold.liquibase_configuration.configuration;

import jakarta.annotation.PostConstruct;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

//@Configuration
public class LiquibaseConfiguration {

    private final DataSourceConfiguration dataSourceConfiguration;

    public LiquibaseConfiguration(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    public DataSource liquibaseDatasource() {
        String jdbcUrl = dataSourceConfiguration.getUrl().replace("r2dbc", "jdbc").replace("///", "//");
        jdbcUrl = jdbcUrl.replace(":postgres:", ":postgresql:");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(dataSourceConfiguration.getUsername());
        dataSource.setPassword(dataSourceConfiguration.getPassword());
        return dataSource;
    }

    @PostConstruct
    public void liquibase() {
        try {
            DataSource dataSource = liquibaseDatasource();
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:/db/db.changelog.yaml");
            liquibase.setShouldRun(true);
            liquibase.afterPropertiesSet();
            dataSource.getConnection().close();
        } catch (LiquibaseException | SQLException e) {
            e.printStackTrace();
        }
    }
}
