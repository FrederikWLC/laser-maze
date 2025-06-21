package model.persistence.serializer.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.token.base.Token;
import model.domain.token.builder.base.TokenBuilder;
import model.domain.token.builder.impl.TargetMirrorTokenBuilder;
import model.domain.token.impl.TargetMirrorToken;

public class TargetMirrorTokenSerializer extends MutableTokenSerializer<TargetMirrorToken> {

    private final String IS_REQUIRED_TARGET_FIELD_NAME = "is_required_target";

    protected TargetMirrorTokenBuilder instantiateBuilder(JsonNode json)  {
        return new TargetMirrorTokenBuilder();
    }

    @Override
    protected TokenBuilder<?,TargetMirrorToken> customizeDeserialization(TokenBuilder<?,TargetMirrorToken> builder, JsonNode json) {
        builder = super.customizeDeserialization(builder, json);
        if (json.has(IS_REQUIRED_TARGET_FIELD_NAME) & json.get(IS_REQUIRED_TARGET_FIELD_NAME).asBoolean()) {
            builder = ((TargetMirrorTokenBuilder) builder).withRequiredTarget();
        }
        return builder;
    }

    @Override
    protected void customizeSerialization(ObjectNode node, Token token) {
        super.customizeSerialization(node, token);
        TargetMirrorToken targetMirrorToken = (TargetMirrorToken) token;
        if (targetMirrorToken.isRequiredTarget()) {
            node.put(IS_REQUIRED_TARGET_FIELD_NAME, true);
        }
    }

}

