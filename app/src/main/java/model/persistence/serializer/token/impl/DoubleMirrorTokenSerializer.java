package model.persistence.serializer.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.DoubleMirrorTokenBuilder;
import model.domain.token.impl.DoubleMirrorToken;
import model.persistence.serializer.token.base.MutableTokenSerializer;

public class DoubleMirrorTokenSerializer extends MutableTokenSerializer<DoubleMirrorToken> {

    protected DoubleMirrorTokenBuilder instantiateBuilder(JsonNode json) {
        return new DoubleMirrorTokenBuilder();
    }

}