package me.sebprunier.demo.testcontainers.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface Jsonable {

    default String toJsonString() {
        return toJson().toString();
    }

    default JsonNode toJson() {
        return toJson(new ObjectMapper());
    }

    JsonNode toJson(ObjectMapper objectMapper);

}