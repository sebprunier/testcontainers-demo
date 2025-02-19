package me.sebprunier.demo.testcontainers_live;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import me.sebprunier.demo.testcontainers.TestcontainersDemoApplication;
import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.DataProviderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.ToxiproxyContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// TODO #1 - create a PostgreSQLContainer, check that container is running and print jdbcurl, user and password

// TODO #2 - create a Test for dataProviderRepository.findById("insee") / @DynamicPropertySource / DynamicPropertyRegistry

// TODO #3 - create a network error and a failing test for dataProviderRepository.findById("insee")
// TODO #3.1 - create a shared Network and add an alias for the PostgreSQLContainer
// TODO #3.2 - create a ToxiproxyContainer with image "ghcr.io/shopify/toxiproxy:2.5.0"
// TODO #3.3 - create a static ToxiproxyClient and configure it in a @BeforeAll method / new ToxiproxyClient(...)
// TODO #3.3 - create a Proxy and configure it in a @BeforeEach method
// TODO #3.4 - delete the Proxy in a @AfterEach method / TOXIPROXYCLIENT.createProxy(...) / "0.0.0.0:8666" / "postgresql:5432"
// TODO #3.4 - change the jdbcUrl in the dynamic configuration
// TODO #3.6 - create the failing test / cut the postgresql connection

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestcontainersDemoApplication.class)
public class Test99_FullExample {
    
    @Test
    public void testIsRunning() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindById() {
        fail("Not yet implemented");
    }

    @Test
    public void testWhenPostgresqlIsUnavailable() {
        fail("Not yet implemented");
    }













    // proxy = TOXIPROXYCLIENT.createProxy(
    //         "postgresql-proxy",
    //         "0.0.0.0:8666",
    //         "postgresql:5432"
    //         );


    // String jdbcUrlTemplate = "jdbc:postgresql://{HOST}:{PORT}/{DATABASE}";
    // String jdbcUrl = jdbcUrlTemplate
    //         .replace("{HOST}", TOXIPROXY_CONTAINER.getHost())
    //         .replace("{PORT}", TOXIPROXY_CONTAINER.getMappedPort(8666).toString())
    //         .replace("{DATABASE}", POSTGRE_SQL_CONTAINER.getDatabaseName());

}

