package me.sebprunier.demo.testcontainers.repositories.data;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers_live.Test06_RegionRepositoryTests;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class DataProviderRepositoryTests_Toxiproxy {

    private static final Network SHARED_NETWORK = Network.newNetwork();
    private static final String SHARED_NETWORK_ALIAS = "postgresql";

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("database/ups/v001__data_sources.sql")
            .withNetwork(SHARED_NETWORK)
            .withNetworkAliases(SHARED_NETWORK_ALIAS);

    @Container
    private static final ToxiproxyContainer TOXIPROXY_CONTAINER = new ToxiproxyContainer("ghcr.io/shopify/toxiproxy:2.5.0")
            .withNetwork(SHARED_NETWORK);

    private static Proxy TOXIPROXY;

    @Autowired
    private DataProviderRepository dataProviderRepository;

    @BeforeAll
    public static void beforeAll() throws Exception {
        ToxiproxyClient toxiproxyClient = new ToxiproxyClient(
                TOXIPROXY_CONTAINER.getHost(),
                TOXIPROXY_CONTAINER.getControlPort()
        );
        TOXIPROXY = toxiproxyClient.createProxy(
                SHARED_NETWORK_ALIAS,
                "0.0.0.0:8666",
                "postgresql:5432"
        );
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
            TOXIPROXY.toxics().resetPeer("CUT_CONNECTION_DOWNSTREAM", ToxicDirection.DOWNSTREAM, 0);
            dataProviderRepository.findById("insee");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertInstanceOf(CannotGetJdbcConnectionException.class, e);
        }
    }

}
