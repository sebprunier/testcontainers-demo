package me.sebprunier.demo.testcontainers_live;

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

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class Test08_DataProviderRepositoryTests_Toxiproxy {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("database/ups/v001__data_sources.sql");

    // TODO create a ToxiproxyContainer

    @Autowired
    private DataProviderRepository dataProviderRepository;

    // TODO @BeforeAll : create the Toxiproxy instance

    @DynamicPropertySource
    public static void overrideApplicationProperties(DynamicPropertyRegistry registry) {
        // TODO use Toxiproxy config for jdbc url
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);

        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    public void testFindByIdWithoutConnection() {
        try {
            // TODO shutdown the database connection!
            dataProviderRepository.findById("insee");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertInstanceOf(CannotGetJdbcConnectionException.class, e);
        }
    }

}
