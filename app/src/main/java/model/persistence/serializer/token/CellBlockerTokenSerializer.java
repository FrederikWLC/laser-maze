package model.persistence.serializer.token;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.CellBlockerTokenBuilder;
import model.domain.token.impl.CellBlockerToken;
import model.persistence.serializer.ISerializer;

public class CellBlockerTokenSerializer extends TokenSerializer<CellBlockerToken> implements ISerializer<CellBlockerToken> {

    protected CellBlockerTokenBuilder instantiateBuilder(JsonNode json) {
        return new CellBlockerTokenBuilder();
    }

}
