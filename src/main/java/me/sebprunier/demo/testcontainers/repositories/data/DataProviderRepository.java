package me.sebprunier.demo.testcontainers.repositories.data;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.mappers.DataProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Optional<DataProvider> findById(String id) {
        String sql = "SELECT id, name, url, description FROM Data_Provider WHERE id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        List<DataProvider> dataProviderList = template.query(sql, param, dataProviderMapper);

        return dataProviderList.stream().findFirst();
    }

    public List<DataProvider> findAll() {
        String sql = "SELECT id, name, url, description FROM Data_Provider";

        return template.query(sql, dataProviderMapper);
    }

}
