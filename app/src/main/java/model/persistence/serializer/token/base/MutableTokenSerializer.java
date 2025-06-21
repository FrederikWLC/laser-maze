package model.persistence.serializer.token.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.board.Direction;
import model.domain.token.base.MutableToken;
import model.domain.token.base.Token;
import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.builder.base.TokenBuilder;
import model.persistence.serializer.util.FieldNameRegistry;

public abstract class MutableTokenSerializer<T extends MutableToken> extends TokenSerializer<T> {


    public MutableTokenSerializer() {
        super();
    }

    @Override
    protected TokenBuilder<?,T> customizeDeserialization(TokenBuilder<?,T> builder, JsonNode json) {
        builder = super.customizeDeserialization(builder, json);

        // Extract direction, movable, and turnable properties from JSON
        Direction direction = json.has(FieldNameRegistry.DIRECTION_FIELD_NAME)
                ? Direction.valueOf(json.get(FieldNameRegistry.DIRECTION_FIELD_NAME).asText()) : null; // Default to null if not present
        boolean movable = json.has(FieldNameRegistry.MOVABLE_FIELD_NAME)
                ? json.get(FieldNameRegistry.MOVABLE_FIELD_NAME).asBoolean() : true; // Default to true if not present
        boolean turnable = json.has(FieldNameRegistry.TURNABLE_FIELD_NAME)
                ? json.get(FieldNameRegistry.TURNABLE_FIELD_NAME).asBoolean(): true; // Default to true if not present

        // If direction is not null, set it in the builder
        if (direction != null) {
            builder = ((MutableTokenBuilder) builder).withDirection(direction);
        }

        builder = ((MutableTokenBuilder) builder).withMutability(movable,turnable);
        return builder;
    }

    protected void customizeSerialization(ObjectNode node, Token token) {
        super.customizeSerialization(node, token);
        MutableToken mutableToken = (MutableToken) token;
        if (mutableToken.isTurned()) { // Only include direction if the token has one
            node.put(FieldNameRegistry.DIRECTION_FIELD_NAME, mutableToken.getDirection().toString());
        }
        node.put(FieldNameRegistry.MOVABLE_FIELD_NAME, mutableToken.isMovable());
        node.put(FieldNameRegistry.TURNABLE_FIELD_NAME, mutableToken.isTurnable());
    }

    protected abstract MutableTokenBuilder<?, T> instantiateBuilder(JsonNode json);

}


