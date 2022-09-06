package me.sebprunier.demo.testcontainers.models.territoire;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.sebprunier.demo.testcontainers.models.Jsonable;

public class Region implements Jsonable {

    public final String code;
    public final String libelle;

    public Region(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }


    @Override
    public JsonNode toJson(ObjectMapper objectMapper) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode
                .put("code", code)
                .put("libelle", libelle);
        return jsonNode;
    }
}
