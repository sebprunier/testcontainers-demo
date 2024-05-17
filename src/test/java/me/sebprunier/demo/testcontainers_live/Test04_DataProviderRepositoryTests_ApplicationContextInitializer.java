package me.sebprunier.demo.testcontainers_live;

import me.sebprunier.demo.testcontainers.TestcontainersDemoApplication;
import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.DataProviderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestcontainersDemoApplication.class)
@Testcontainers
//@ContextConfiguration(initializers = Test04_DataProviderRepositoryTests_ApplicationContextInitializer.Initializer.class)
public class Test04_DataProviderRepositoryTests_ApplicationContextInitializer {

    private final Logger logger = LoggerFactory.getLogger(Test04_DataProviderRepositoryTests_ApplicationContextInitializer.class);

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("database/ups/v001__data_sources.sql");

    @Autowired
    private DataProviderRepository dataProviderRepository;

    // TODO create a custom static ApplicationContextInitializer<ConfigurableApplicationContext>

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

    /*
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword()
        );
     */

}
