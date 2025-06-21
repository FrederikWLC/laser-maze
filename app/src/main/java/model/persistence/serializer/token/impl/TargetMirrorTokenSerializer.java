package model.persistence.serializer.token.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.token.base.Token;
import model.domain.token.builder.base.TokenBuilder;
import model.domain.token.builder.impl.TargetMirrorTokenBuilder;
import model.domain.token.impl.TargetMirrorToken;
import model.persistence.serializer.util.FieldNameRegistry;
import model.persistence.serializer.token.base.MutableTokenSerializer;

public class TargetMirrorTokenSerializer extends MutableTokenSerializer<TargetMirrorToken> {

    protected TargetMirrorTokenBuilder instantiateBuilder(JsonNode json)  {
        return new TargetMirrorTokenBuilder();
    }

    @Override
    protected TokenBuilder<?,TargetMirrorToken> customizeDeserialization(TokenBuilder<?,TargetMirrorToken> builder, JsonNode json) {
        builder = super.customizeDeserialization(builder, json);
        // Default to false if not present in json
        boolean isRequiredTarget = json.has(FieldNameRegistry.IS_REQUIRED_TARGET_FIELD_NAME)
                ? json.get(FieldNameRegistry.IS_REQUIRED_TARGET_FIELD_NAME).asBoolean() : false;
        // Build by casting to TargetMirrorTokenBuilder
        builder = ((TargetMirrorTokenBuilder) builder).withRequiredTarget(isRequiredTarget);
        return builder;
    }

    @Override
    protected void customizeSerialization(ObjectNode node, Token token) {
        super.customizeSerialization(node, token);
        TargetMirrorToken targetMirrorToken = (TargetMirrorToken) token;
        if (targetMirrorToken.isRequiredTarget()) {
            node.put(FieldNameRegistry.IS_REQUIRED_TARGET_FIELD_NAME, true);
        }
    }

}

