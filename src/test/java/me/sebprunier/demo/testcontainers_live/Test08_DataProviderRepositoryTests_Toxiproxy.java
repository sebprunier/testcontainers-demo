package me.sebprunier.demo.testcontainers_live;

import me.sebprunier.demo.testcontainers.TestcontainersDemoApplication;
import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.DataProviderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestcontainersDemoApplication.class)
@Testcontainers
public class Test08_DataProviderRepositoryTests_Toxiproxy {

    // TODO define a shared Network

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("database/ups/v001__data_sources.sql");

    // TODO create a ToxiproxyContainer from image "ghcr.io/shopify/toxiproxy:2.5.0"

    @Autowired
    private DataProviderRepository dataProviderRepository;

    // TODO @BeforeAll : create the ToxiproxyClient static instance

    // TODO @BeforeEach : create the Proxy instance

    // TODO @AfterEach : delete the Proxy instance

    @DynamicPropertySource
    public static void overrideApplicationProperties(DynamicPropertyRegistry registry) {
        // TODO use Toxiproxy config for jdbc url
        // String jdbcUrlTemplate = "jdbc:postgresql://{HOST}:{PORT}/{DATABASE}";
        // String jdbcUrl = jdbcUrlTemplate
        //        .replace("{HOST}", xxx)
        //        .replace("{PORT}", xxx)
        //        .replace("{DATABASE}", xxx);

        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);

        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    public void testFindById() {
        Optional<DataProvider> providerOpt = dataProviderRepository.findById("insee");
        assertTrue(providerOpt.isPresent());
        DataProvider provider = providerOpt.get();
        assertEquals("insee", provider.id);
        assertEquals("Insee", provider.name);
        assertTrue(provider.urlOpt.isPresent());
        assertEquals("https://www.insee.fr", provider.urlOpt.get());
        assertTrue(provider.descriptionOpt.isPresent());
        assertEquals("Institut national de la statistique et des études économiques", provider.descriptionOpt.get());
    }

    @Test
    public void testFindByIdWithoutConnection() {
        try {
            // TODO shutdown the database connection!
            dataProviderRepository.findById("insee");
            fail("Test should throw an Exception!");
        } catch (Exception e) {
            e.printStackTrace();
            assertInstanceOf(CannotGetJdbcConnectionException.class, e);
        }
    }

}
