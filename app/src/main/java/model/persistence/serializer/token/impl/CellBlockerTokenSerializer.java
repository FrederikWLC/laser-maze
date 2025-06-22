package model.persistence.serializer.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.CellBlockerTokenBuilder;
import model.domain.token.impl.CellBlockerToken;
import model.persistence.serializer.Serializer;
import model.persistence.serializer.token.base.TokenSerializer;

public class CellBlockerTokenSerializer extends TokenSerializer<CellBlockerToken> {

    protected CellBlockerTokenBuilder instantiateBuilder(JsonNode json) {
        return new CellBlockerTokenBuilder();
    }

}
