package me.sebprunier.demo.testcontainers.repositories.data;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers_live.Test06_RegionRepositoryTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.net.SocketTimeoutException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class DataProviderRepositoryTests_Toxiproxy {

    private static final Network SHARED_NETWORK = Network.newNetwork();

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("database/ups/v001__data_sources.sql")
            .withNetwork(SHARED_NETWORK)
            .withNetworkAliases("postgresql");

    @Container
    private static final ToxiproxyContainer TOXIPROXY_CONTAINER = new ToxiproxyContainer("ghcr.io/shopify/toxiproxy:2.5.0")
            .withNetwork(SHARED_NETWORK);

    private static ToxiproxyClient TOXIPROXYCLIENT;
    private Proxy proxy;

    @Autowired
    private DataProviderRepository dataProviderRepository;

    @BeforeAll
    public static void beforeAll() throws Exception {
        TOXIPROXYCLIENT = new ToxiproxyClient(
                TOXIPROXY_CONTAINER.getHost(),
                TOXIPROXY_CONTAINER.getControlPort()
        );
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        proxy = TOXIPROXYCLIENT.createProxy(
                "postgresql-proxy",
                "0.0.0.0:8666",
                "postgresql:5432"
        );
    }

    @AfterEach
    public void afterEach() throws Exception {
        proxy.delete();
    }

    @DynamicPropertySource
    public static void overrideApplicationProperties(DynamicPropertyRegistry registry) {
        String jdbcUrlTemplate = "jdbc:postgresql://{HOST}:{PORT}/{DATABASE}";
        String jdbcUrl = jdbcUrlTemplate
                .replace("{HOST}", TOXIPROXY_CONTAINER.getHost())
                .replace("{PORT}", TOXIPROXY_CONTAINER.getMappedPort(8666).toString())
                .replace("{DATABASE}", POSTGRESQL_CONTAINER.getDatabaseName());

        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    public void testFindByIdWithoutConnection() {
        try {
            proxy.toxics().resetPeer("CUT_CONNECTION_DOWNSTREAM", ToxicDirection.DOWNSTREAM, 0);
            dataProviderRepository.findById("insee");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertInstanceOf(CannotGetJdbcConnectionException.class, e);
        }
    }

    @Test
    public void testFindByIdWithConnectionTimeout() {
        try {
            proxy.toxics().timeout("TIMEOUT_CONNECTION_DOWNSTREAM", ToxicDirection.DOWNSTREAM, 0);
            dataProviderRepository.findById("insee");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertInstanceOf(SocketTimeoutException.class, getRootCause(e));
        }
    }

    private static Throwable getRootCause(Exception e) {
        Throwable rootCause = e;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

}
