package me.sebprunier.demo.testcontainers.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import me.sebprunier.demo.testcontainers.models.Jsonable;

import java.util.ArrayList;
import java.util.List;

public class FakeJsonable implements Jsonable {

    public final String id;
    public final String name;

    public FakeJsonable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public JsonNode toJson(ObjectMapper objectMapper) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        return jsonNode
                .put("id", id)
                .put("name", name);
    }

    public static FakeJsonable mock() {
        return mock(1).get(0);
    }

    public static List<FakeJsonable> mock(int n) {
        Faker faker = new Faker();
        List<FakeJsonable> mocks = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            mocks.add(new FakeJsonable(
                    faker.idNumber().valid(),
                    faker.name().username()
            ));
        }
        return mocks;
    }
}
