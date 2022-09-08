package me.sebprunier.demo.testcontainers_live;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test02_PostgreSQLContainerExample {

    private final Logger logger = LoggerFactory.getLogger(Test02_PostgreSQLContainerExample.class);

    // TODO Create PostgreSQL container
    // TODO force username and password, and show that the port cannot be set

    @Test
    public void testIsRunning() {
        // TODO check that container is running and log jdbc url, user and password
    }

}
