package me.sebprunier.demo.testcontainers.utils;

import me.sebprunier.demo.testcontainers.models.Jsonable;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ControllerUtils {

    private ControllerUtils() {
    }

    public static String badRequest(HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return "{ \"message\": \"Bad Request\" }";
    }

    public static String notFound(HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "{ \"message\": \"Not Found\" }";
    }

    public static String processOptionalString(Optional<String> strOpt, HttpServletResponse response) {
        if (strOpt.isPresent()) {
            response.setStatus(HttpStatus.OK.value());
            return strOpt.get();
        } else {
            return notFound(response);
        }
    }

    public static String processOptionalJsonable(Optional<? extends Jsonable> jsonableOpt, HttpServletResponse response) {
        return processOptionalString(jsonableOpt.map(Jsonable::toJsonString), response);
    }

}
