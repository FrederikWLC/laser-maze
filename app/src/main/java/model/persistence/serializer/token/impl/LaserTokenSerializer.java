package model.persistence.serializer.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.LaserTokenBuilder;
import model.domain.token.impl.LaserToken;
import model.persistence.serializer.token.base.MutableTokenSerializer;


public class LaserTokenSerializer extends MutableTokenSerializer<LaserToken> {

    protected LaserTokenBuilder instantiateBuilder(JsonNode json) {
        return new LaserTokenBuilder();
    }

}