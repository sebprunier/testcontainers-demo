package me.sebprunier.demo.testcontainers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

// The extension finds all fields that are annotated with @Container and calls their container lifecycle methods
// (methods on the Startable interface).
@Testcontainers
public class PostgreSQLContainerTests {

    private final Logger logger = LoggerFactory.getLogger(PostgreSQLContainerTests.class);

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

}