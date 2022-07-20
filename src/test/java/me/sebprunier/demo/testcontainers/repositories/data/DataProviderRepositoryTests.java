package me.sebprunier.demo.testcontainers.repositories.data;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class DataProviderRepositoryTests {

    private final Logger logger = LoggerFactory.getLogger(DataProviderRepositoryTests.class);

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withUsername("testcontainers_demo_user")
            .withPassword("testcontainers_demo_password")
            .withInitScript("database/ups/v001__data_sources.sql");

    @Autowired
    private DataProviderRepository dataProviderRepository;

    @DynamicPropertySource
    public static void overrideApplicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
    }

    @Test
    public void testFindById() throws ExecutionException, InterruptedException {
        Optional<DataProvider> providerOpt = dataProviderRepository.findById("insee").get();
        assertTrue(providerOpt.isPresent());
        DataProvider provider = providerOpt.get();
        assertEquals("insee", provider.id);
        assertEquals("Insee", provider.name);
        assertTrue(provider.urlOpt.isPresent());
        assertEquals("https://www.insee.fr", provider.urlOpt.get());
        assertTrue(provider.descriptionOpt.isPresent());
        assertEquals("Institut national de la statistique et des études économiques", provider.descriptionOpt.get());
    }

}
