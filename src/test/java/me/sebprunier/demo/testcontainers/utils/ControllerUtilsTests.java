package me.sebprunier.demo.testcontainers.utils;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerUtilsTests {

    @Test
    public void testBadRequest() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String badRequestPayload = ControllerUtils.badRequest(response);
        assertEquals("{ \"message\": \"Bad Request\" }", badRequestPayload);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testNotFound() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String badRequestPayload = ControllerUtils.notFound(response);
        assertEquals("{ \"message\": \"Not Found\" }", badRequestPayload);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testProcessOptionalStringWithEmpty() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String payload = ControllerUtils.processOptionalString(Optional.empty(), response);
        assertEquals("{ \"message\": \"Not Found\" }", payload);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testProcessOptionalString() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String payload = ControllerUtils.processOptionalString(Optional.of("Hello world"), response);
        assertEquals("Hello world", payload);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testProcessOptionalJsonableWithEmpty() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String payload = ControllerUtils.processOptionalJsonable(Optional.empty(), response);
        assertEquals("{ \"message\": \"Not Found\" }", payload);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testProcessOptionalJsonable() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String payload = ControllerUtils.processOptionalJsonable(Optional.of(new FakeJsonable("123", "name1")), response);
        assertEquals("{\"id\":\"123\",\"name\":\"name1\"}", payload);
        assertEquals(200, response.getStatus());
    }

}
