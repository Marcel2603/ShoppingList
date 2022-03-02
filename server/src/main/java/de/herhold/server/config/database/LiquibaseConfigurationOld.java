package de.herhold.server.config.database;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfigurationOld {
//
//    private final DataSourceConfiguration dataSourceConfiguration;
//
//    public LiquibaseConfiguration(DataSourceConfiguration dataSourceConfiguration) {
//        this.dataSourceConfiguration = dataSourceConfiguration;
//    }
//
//    public DataSource liquibaseDatasource() {
//        String jdbcUrl = dataSourceConfiguration.getUrl().replace("r2dbc", "jdbc").replace("///", "//");
//        jdbcUrl = jdbcUrl.replace(":postgres:", ":postgresql:");
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(jdbcUrl);
//        dataSource.setUsername(dataSourceConfiguration.getUsername());
//        dataSource.setPassword(dataSourceConfiguration.getPassword());
//        return dataSource;
//    }
//
//    @PostConstruct
//    public void liquibase() {
//        try {
//            DataSource dataSource = liquibaseDatasource();
//            SpringLiquibase liquibase = new SpringLiquibase();
//            liquibase.setDataSource(dataSource);
//            liquibase.setChangeLog("classpath:/db/db.changelog.yaml");
//            liquibase.setShouldRun(true);
//            liquibase.afterPropertiesSet();
//            dataSource.getConnection().close();
//        } catch (LiquibaseException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
