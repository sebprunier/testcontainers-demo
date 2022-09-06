package me.sebprunier.demo.testcontainers.repositories.territoire.mappers;

import me.sebprunier.demo.testcontainers.models.territoire.Region;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RegionMapper implements RowMapper<Region> {

    @Override
    public Region mapRow(ResultSet resultSet, int i) throws SQLException {
        String code = resultSet.getString("reg");
        String libelle = resultSet.getString("libelle");
        return new Region(
                code,
                libelle
        );
    }

}
