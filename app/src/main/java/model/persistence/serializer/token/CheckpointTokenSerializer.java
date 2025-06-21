package model.persistence.serializer.token;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.CheckpointTokenBuilder;
import model.domain.token.impl.CheckpointToken;

public class CheckpointTokenSerializer extends MutableTokenSerializer<CheckpointToken> {

    protected CheckpointTokenBuilder instantiateBuilder(JsonNode json) {
        return new CheckpointTokenBuilder();
    }

}