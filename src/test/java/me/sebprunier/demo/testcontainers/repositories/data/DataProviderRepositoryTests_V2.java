package me.sebprunier.demo.testcontainers.repositories.data;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class DataProviderRepositoryTests_V2 {

    private final Logger logger = LoggerFactory.getLogger(DataProviderRepositoryTests_V2.class);

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withUsername("testcontainers_demo_user")
            .withPassword("testcontainers_demo_password")
            .withInitScript("database/ups/v001__data_sources.sql");


    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword()
            );
        }
    }

    @Autowired
    private DataProviderRepository dataProviderRepository;


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

}
