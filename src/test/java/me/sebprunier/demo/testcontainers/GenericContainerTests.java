package me.sebprunier.demo.testcontainers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class GenericContainerTests {

    private final Logger logger = LoggerFactory.getLogger(GenericContainerTests.class);

    @Container
    private static final GenericContainer CONTAINER = new GenericContainer(DockerImageName.parse("alpine:3.16")).withCommand("top");

    @Test
    public void testIsRunning() {
        assertTrue(CONTAINER.isRunning());
        logger.info("Container id is: {}", CONTAINER.getContainerId());
    }

    @Test
    public void testExecuteCommand() throws Exception {
        org.testcontainers.containers.Container.ExecResult execResult = CONTAINER.execInContainer("ls", "-al");
        assertEquals(0, execResult.getExitCode());
        logger.info(execResult.getStdout());
    }

}
