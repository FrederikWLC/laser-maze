package model.persistence.serializer.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import model.domain.token.builder.impl.BeamSplitterTokenBuilder;
import model.domain.token.impl.BeamSplitterToken;
import model.persistence.serializer.token.base.MutableTokenSerializer;


public class BeamSplitterTokenSerializer extends MutableTokenSerializer<BeamSplitterToken> {

    protected BeamSplitterTokenBuilder instantiateBuilder(JsonNode json) {
        return new BeamSplitterTokenBuilder();
    }

}