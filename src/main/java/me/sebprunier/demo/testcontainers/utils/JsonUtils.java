package me.sebprunier.demo.testcontainers.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import me.sebprunier.demo.testcontainers.models.Jsonable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JsonUtils {

    private JsonUtils() {
    }

    public static Optional<JsonNode> ofNullableNode(JsonNode node) {
        return Optional.ofNullable(node).filter(n -> !n.isNull());
    }

    public static ArrayNode toJsonArray(List<? extends Jsonable> jsonableElements) {
        return toJsonArray(jsonableElements, new ObjectMapper());
    }

    public static ArrayNode toJsonArray(List<? extends Jsonable> jsonableElements, ObjectMapper objectMapper) {
        return toJsonArray(jsonableElements, objectMapper, element -> element.toJson(objectMapper));
    }

    public static <T extends Jsonable> ArrayNode toJsonArray(List<T> objects, ObjectMapper objectMapper, Function<T, JsonNode> jsonMapper) {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        return arrayNode.addAll(objects.stream().map(jsonMapper).collect(Collectors.toList()));
    }

}