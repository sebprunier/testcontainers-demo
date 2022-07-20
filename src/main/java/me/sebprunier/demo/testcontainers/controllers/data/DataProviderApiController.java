package me.sebprunier.demo.testcontainers.controllers.data;

import me.sebprunier.demo.testcontainers.models.data.DataProvider;
import me.sebprunier.demo.testcontainers.repositories.data.DataProviderRepository;
import me.sebprunier.demo.testcontainers.utils.ControllerUtils;
import me.sebprunier.demo.testcontainers.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class DataProviderApiController {

    private final DataProviderRepository dataProviderRepository;

    @Autowired
    public DataProviderApiController(DataProviderRepository dataProviderRepository) {
        this.dataProviderRepository = dataProviderRepository;
    }

    @GetMapping(path = "/api/v1/data/providers", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String all() {
        List<DataProvider> dataProviderList = dataProviderRepository.findAll();
        return JsonUtils.toJsonArray(dataProviderList).toString();
    }

    @GetMapping(path = "/api/v1/data/providers/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String byId(@PathVariable String id, HttpServletResponse response) {
        Optional<DataProvider> dataProviderOpt = dataProviderRepository.findById(id);
        return ControllerUtils.processOptionalJsonable(dataProviderOpt, response);
    }

}
