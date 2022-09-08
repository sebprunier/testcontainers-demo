package me.sebprunier.demo.testcontainers_live;

import me.sebprunier.demo.testcontainers.CustomPostgreSQLContainer;
import me.sebprunier.demo.testcontainers.TestcontainersDemoApplication;
import me.sebprunier.demo.testcontainers.models.territoire.Region;
import me.sebprunier.demo.testcontainers.repositories.territoire.RegionRepository;
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
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestcontainersDemoApplication.class)
@Testcontainers
public class Test06_RegionRepositoryTests {

    private final Logger logger = LoggerFactory.getLogger(Test06_RegionRepositoryTests.class);

    // FIXME this does not work! => use postgis/postgis:12-3.2
    @Container
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12")
            .withInitScript("database/ups/v001__data_sources.sql")
            .withInitScript("database/ups/v002__regions.sql");

    @Autowired
    private RegionRepository regionRepository;

    @DynamicPropertySource
    public static void overrideApplicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    public void testFindByCode() {
        Optional<Region> regionOpt = regionRepository.findByCode("01");
        assertTrue(regionOpt.isPresent());
        Region region = regionOpt.get();
        assertEquals("01", region.code);
        assertEquals("Guadeloupe", region.libelle);
    }

    @Test
    public void testFindByLatLon() {
        List<Region> regions = regionRepository.findByLatLon(46.785948, 0.434537);
        assertEquals(1, regions.size());
        Region region = regions.get(0);
        assertEquals("75", region.code);
        assertEquals("Nouvelle-Aquitaine", region.libelle);
    }

}
