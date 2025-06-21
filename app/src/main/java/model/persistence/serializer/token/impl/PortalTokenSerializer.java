package model.persistence.serializer.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.PortalTokenBuilder;
import model.domain.token.impl.PortalToken;
import model.persistence.serializer.token.base.MutableTokenSerializer;


public class PortalTokenSerializer extends MutableTokenSerializer<PortalToken> {

    protected PortalTokenBuilder instantiateBuilder(JsonNode json) {
        return new PortalTokenBuilder();
    }

}