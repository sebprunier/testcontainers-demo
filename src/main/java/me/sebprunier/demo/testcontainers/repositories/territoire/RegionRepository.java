package me.sebprunier.demo.testcontainers.repositories.territoire;

import me.sebprunier.demo.testcontainers.models.territoire.Region;
import me.sebprunier.demo.testcontainers.repositories.territoire.mappers.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RegionRepository {

    private final NamedParameterJdbcTemplate template;
    private final RegionMapper regionMapper;

    @Autowired
    public RegionRepository(
            NamedParameterJdbcTemplate template,
            RegionMapper regionMapper
    ) {
        this.template = template;
        this.regionMapper = regionMapper;
    }

    public Optional<Region> findByCode(String code) {
        String sql = "SELECT reg, libelle FROM Region WHERE reg = :code";

        SqlParameterSource param = new MapSqlParameterSource().addValue("code", code);
        List<Region> regions = template.query(sql, param, regionMapper);

        return regions.stream().findFirst();
    }

    public List<Region> findAll() {
        String sql = "SELECT reg, libelle FROM Region";

        return template.query(sql, regionMapper);
    }

    public List<Region> findByLatLon(Double lat, Double lon) {
        String sql = "SELECT r.reg, r.libelle\n" +
                "FROM Region r\n" +
                "INNER JOIN Region_Contour rc ON rc.reg = r.reg\n" +
                "WHERE ST_Contains(rc.contour, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326))";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("lon", lon)
                .addValue("lat", lat);

        return template.query(sql, params, regionMapper);
    }

    public Optional<String> contour(String code) {
        String sql = "SELECT ST_AsGeoJson(contour) FROM Region_Contour WHERE reg = :code";

        SqlParameterSource param = new MapSqlParameterSource().addValue("code", code);
        String contour = template.queryForObject(sql, param, String.class);

        return Optional.ofNullable(contour);
    }

}
