package me.sebprunier.demo.testcontainers.controllers.doc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentationController {

    @GetMapping("/doc")
    public String doc() {
        return "documentation";
    }

}
