package me.sebprunier.demo.testcontainers_live;

import me.sebprunier.demo.testcontainers.CustomPostgreSQLContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class Test07_DatabaseEvolutionsTests {

    @Container
    private static final CustomPostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new CustomPostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:12-3.2").asCompatibleSubstituteFor("postgres"));

    @Test
    void testEvolutions() {
        // TODO execute UPs and DOWNs using ScriptUtils
    }

}