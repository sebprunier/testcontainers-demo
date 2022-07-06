package me.sebprunier.demo.testcontainers.controllers.doc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentationControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void testDocumentation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/doc"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("swagger-ui")));
    }

}
