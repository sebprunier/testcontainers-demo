package me.sebprunier.demo.testcontainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

// The extension finds all fields that are annotated with @Container and calls their container lifecycle methods
// (methods on the Startable interface).
@Testcontainers
public class DatabaseTests {

    private final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    // Containers declared as static fields will be shared between test methods.
    // They will be started only once before any test method is executed and stopped after the last test method has executed
    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12");

    /*
    // Containers declared as instance fields will be started and stopped for every test method.
    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12");
     */

    @Test
    void testIsRunning() {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        logger.info("Container Jdbc Url: {}", POSTGRESQL_CONTAINER.getJdbcUrl());
        logger.info("Container Username: {}", POSTGRESQL_CONTAINER.getUsername());
        logger.info("Container Password: {}", POSTGRESQL_CONTAINER.getPassword());
    }

    @Test
    void testEvolutions() {
        try {
            Connection connection = POSTGRESQL_CONTAINER.createConnection("");

            // Ups
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/ups/v001__data_sources.sql"));

            // Downs
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/downs/v001__data_sources.sql"));

        } catch (SQLException e) {
            Assertions.fail(e);
        }
    }

}