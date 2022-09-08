package me.sebprunier.demo.testcontainers_live;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.DataProviderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class Test03_DataProviderRepositoryTests {

    private final Logger logger = LoggerFactory.getLogger(Test03_DataProviderRepositoryTests.class);

    @Test
    public void testFindById() {
        Optional<DataProvider> providerOpt = dataProviderRepository().findById("insee");

        assertTrue(providerOpt.isPresent());
        DataProvider provider = providerOpt.get();
        assertEquals("insee", provider.id);
        assertEquals("Insee", provider.name);
        assertTrue(provider.urlOpt.isPresent());
        assertEquals("https://www.insee.fr", provider.urlOpt.get());
        assertTrue(provider.descriptionOpt.isPresent());
        assertEquals("Institut national de la statistique et des études économiques", provider.descriptionOpt.get());
    }

    private DataProviderRepository dataProviderRepository() {
        // TODO create a DataProviderRepository manually (the DataSource can be created with a DataSourceBuilder)
        return null;
    }

}
