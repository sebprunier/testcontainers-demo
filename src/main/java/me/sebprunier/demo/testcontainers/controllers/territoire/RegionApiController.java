package me.sebprunier.demo.testcontainers.controllers.territoire;

import jakarta.servlet.http.HttpServletResponse;
import me.sebprunier.demo.testcontainers.models.territoire.Region;
import me.sebprunier.demo.testcontainers.repositories.territoire.RegionRepository;
import me.sebprunier.demo.testcontainers.utils.ControllerUtils;
import me.sebprunier.demo.testcontainers.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RegionApiController {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionApiController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping(path = "/api/v1/regions", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String find(
            @RequestParam("lat") Optional<Double> latOpt,
            @RequestParam("lon") Optional<Double> lonOpt
    ) {
        if (latOpt.isPresent() && lonOpt.isPresent()) {
            List<Region> regions = regionRepository.findByLatLon(latOpt.get(), lonOpt.get());
            return JsonUtils.toJsonArray(regions).toString();
        } else {
            List<Region> regions = regionRepository.findAll();
            return JsonUtils.toJsonArray(regions).toString();
        }

    }

    @GetMapping(path = "/api/v1/regions/{code}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String byCode(@PathVariable String code, HttpServletResponse response) {
        Optional<Region> regionOpt = regionRepository.findByCode(code);
        return ControllerUtils.processOptionalJsonable(regionOpt, response);
    }

    @GetMapping(path = "/api/v1/regions/{code}/contour", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String contour(@PathVariable String code, HttpServletResponse response) {
        Optional<String> contourOpt = regionRepository.contour(code);
        return ControllerUtils.processOptionalString(contourOpt, response);
    }

}
