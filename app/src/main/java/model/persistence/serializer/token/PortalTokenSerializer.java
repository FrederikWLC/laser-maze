package model.persistence.serializer.token;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.PortalTokenBuilder;
import model.domain.token.impl.PortalToken;


public class PortalTokenSerializer extends MutableTokenSerializer<PortalToken> {

    protected PortalTokenBuilder instantiateBuilder(JsonNode json) {
        return new PortalTokenBuilder();
    }

}