package me.sebprunier.demo.testcontainers.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import me.sebprunier.demo.testcontainers.models.Jsonable;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilsTests {

    @Test
    public void testOfNullableNodeWithNull() {
        assertTrue(JsonUtils.ofNullableNode(null).isEmpty());
    }

    @Test
    public void testOfNullableNodeWithNullNode() {
        assertTrue(JsonUtils.ofNullableNode(NullNode.instance).isEmpty());
    }

    @Test
    public void testOfNullableNodeWithNonNullNode() throws Exception {
        JsonNode node = new ObjectMapper().readTree("{\"name\": \"toto\"}");
        assertFalse(JsonUtils.ofNullableNode(node).isEmpty());
    }

    @Test
    public void testToJsonArrayEmpty() {
        ArrayNode emptyJsonArray = JsonUtils.toJsonArray(Lists.emptyList());
        String expectedJsonArray = "[]";
        assertEquals(expectedJsonArray, emptyJsonArray.toString());
    }

    @Test
    public void testToJsonArray() {
        Jsonable misc1 = new FakeJsonable("123", "name1");
        Jsonable misc2 = new FakeJsonable("456", "name2");
        ArrayNode jsonArray = JsonUtils.toJsonArray(Arrays.asList(misc1, misc2));
        String expectedJsonArray = "[{\"id\":\"123\",\"name\":\"name1\"},{\"id\":\"456\",\"name\":\"name2\"}]";
        assertEquals(expectedJsonArray, jsonArray.toString());
    }

}
