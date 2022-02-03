package de.herhold.server.config.database;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Slf4j
public class LiquibaseConfiguration {

    private final DataSourceConfiguration dataSourceConfiguration;

    public LiquibaseConfiguration(DataSourceConfiguration dataSourceConfiguration) {
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    public DataSource liquibaseDatasource() {
        String jdbcUrl = dataSourceConfiguration.getUrl().replace("r2dbc", "jdbc").replace("///", "//");
        jdbcUrl = jdbcUrl.replace(":postgres:", ":postgresql:");
        System.out.println(dataSourceConfiguration);
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
            // This is because we are running the process manually. Don't let SpringLiquibase do it.
            liquibase.setShouldRun(true);

            liquibase.afterPropertiesSet();
            dataSource.getConnection().close();
        } catch (LiquibaseException | SQLException e) {
            e.printStackTrace();
        }

        // This runs Liquibase
    }
}
