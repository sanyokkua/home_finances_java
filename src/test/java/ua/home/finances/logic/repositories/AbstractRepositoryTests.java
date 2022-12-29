package ua.home.finances.logic.repositories;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;

public abstract class AbstractRepositoryTests {
    @Container
    public static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            "postgres:15.1").withDatabaseName("app_finance_db").withUsername("admin").withPassword("welcome");

    static DataSource dataSource;

    @BeforeAll
    static void beforeAll() {
        POSTGRE_SQL_CONTAINER.start();

        var builder = DataSourceBuilder.create();
        builder.driverClassName(POSTGRE_SQL_CONTAINER.getDriverClassName());
        builder.url(POSTGRE_SQL_CONTAINER.getJdbcUrl());
        builder.username(POSTGRE_SQL_CONTAINER.getUsername());
        builder.password(POSTGRE_SQL_CONTAINER.getPassword());
        dataSource = builder.build();

        Resource initSchema = new ClassPathResource("schema.sql");
        Resource initData = new ClassPathResource("create-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @AfterAll
    static void afterAll() {
        POSTGRE_SQL_CONTAINER.stop();
    }
}
