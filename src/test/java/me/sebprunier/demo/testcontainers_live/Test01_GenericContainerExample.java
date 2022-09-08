package me.sebprunier.demo.testcontainers_live;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 This is a simple test with a GenericContainer that uses docker image "alpine:3.16"
 */
public class Test01_GenericContainerExample {

    private final Logger logger = LoggerFactory.getLogger(Test01_GenericContainerExample.class);

    @Test
    public void testIsRunning() {
        // TODO check that container is running and log container id
    }

    @Test
    public void testExecuteCommand() throws Exception {
        // TODO execute 'ls -al' command, check exit code and log result
    }

}
