package me.sebprunier.demo.testcontainers.repositories.data;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.mappers.DataProviderMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class DataProviderRepositoryTests_V3 {

    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withUsername("testcontainers_demo_user")
            .withPassword("testcontainers_demo_password")
            .withInitScript("database/ups/v001__data_sources.sql");

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
        return new DataProviderRepository(
                new NamedParameterJdbcTemplate(
                        getTestContainerDataSource()
                ),
                new DataProviderMapper()
        );
    }

    private DataSource getTestContainerDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(POSTGRESQL_CONTAINER.getJdbcUrl());
        dataSourceBuilder.username("testcontainers_demo_user");
        dataSourceBuilder.password("testcontainers_demo_password");
        return dataSourceBuilder.build();
    }
}
