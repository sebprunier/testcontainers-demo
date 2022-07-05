package me.sebprunier.demo.testcontainers.repositories.data;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.mappers.DataProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public class DataProviderRepository {

    private final NamedParameterJdbcTemplate template;
    private final DataProviderMapper dataProviderMapper;

    @Autowired
    public DataProviderRepository(
            NamedParameterJdbcTemplate template,
            DataProviderMapper dataProviderMapper
    ) {
        this.template = template;
        this.dataProviderMapper = dataProviderMapper;
    }

    @Async
    public CompletableFuture<Optional<DataProvider>> findById(String id) {
        String sql = "SELECT id, name, url, description FROM Data_Provider WHERE id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        List<DataProvider> dataProviderList = template.query(sql, param, dataProviderMapper);
        return CompletableFuture.completedFuture(
                dataProviderList.stream().findFirst()
        );
    }

    @Async
    public CompletableFuture<List<DataProvider>> findAll() {
        String sql = "SELECT id, name, url, description FROM Data_Provider";

        return CompletableFuture.completedFuture(
                template.query(sql, dataProviderMapper)
        );
    }

}
