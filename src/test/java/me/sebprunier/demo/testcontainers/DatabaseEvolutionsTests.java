package me.sebprunier.demo.testcontainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.SQLException;

@Testcontainers
public class DatabaseEvolutionsTests {

    @Container
    private static final CustomPostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new CustomPostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:12-3.2").asCompatibleSubstituteFor("postgres"));

    @Test
    void testEvolutions() {
        try {
            Connection connection = POSTGRESQL_CONTAINER.createConnection("");

            // Ups
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/ups/v001__data_sources.sql"));
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/ups/v002__regions.sql"));

            // Downs
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/downs/v002__regions.sql"));
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/downs/v001__data_sources.sql"));

        } catch (SQLException e) {
            Assertions.fail(e);
        }
    }

}