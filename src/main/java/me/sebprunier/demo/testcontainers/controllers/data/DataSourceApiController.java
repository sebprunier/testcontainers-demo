package me.sebprunier.demo.testcontainers.controllers.data;

import me.sebprunier.demo.testcontainers.models.data.DataSource;
import me.sebprunier.demo.testcontainers.repositories.data.DataSourceRepository;
import me.sebprunier.demo.testcontainers.utils.JsonUtils;
import me.sebprunier.demo.testcontainers.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class DataSourceApiController {

    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public DataSourceApiController(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    @Async
    @GetMapping(path = "/api/v1/data/sources", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CompletableFuture<String> all() {
        CompletableFuture<List<DataSource>> dataSourceListFuture = dataSourceRepository.findAll();
        return dataSourceListFuture.thenApply(dataSourceList -> JsonUtils.toJsonArray(dataSourceList).toString());
    }

    @Async
    @GetMapping(path = "/api/v1/data/sources/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CompletableFuture<String> byId(@PathVariable String id, HttpServletResponse response) {
        CompletableFuture<Optional<DataSource>> dataSourceOptFuture = dataSourceRepository.findById(id);
        return dataSourceOptFuture.thenApply(dataSourceOpt -> ControllerUtils.processOptionalJsonable(dataSourceOpt, response));
    }

}
