package me.sebprunier.demo.testcontainers.controllers.data;

import me.sebprunier.demo.testcontainers.models.data.DataSource;
import me.sebprunier.demo.testcontainers.repositories.data.DataSourceRepository;
import me.sebprunier.demo.testcontainers.utils.ControllerUtils;
import me.sebprunier.demo.testcontainers.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class DataSourceApiController {

    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public DataSourceApiController(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    @GetMapping(path = "/api/v1/data/sources", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String all() {
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        return JsonUtils.toJsonArray(dataSourceList).toString();
    }

    @GetMapping(path = "/api/v1/data/sources/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String byId(@PathVariable String id, HttpServletResponse response) {
        Optional<DataSource> dataSourceOpt = dataSourceRepository.findById(id);
        return ControllerUtils.processOptionalJsonable(dataSourceOpt, response);
    }

}
