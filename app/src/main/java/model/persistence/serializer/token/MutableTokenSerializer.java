package model.persistence.serializer.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.board.Direction;
import model.domain.token.base.MutableToken;
import model.domain.token.base.Token;
import model.domain.token.builder.base.MutableTokenBuilder;
import model.domain.token.builder.base.TokenBuilder;

public abstract class MutableTokenSerializer<T extends MutableToken> extends TokenSerializer<T>  {

    private final String DIRECTION_FIELD_NAME = "direction";
    private final String MOVABLE_FIELD_NAME = "movable";
    private final String TURNABLE_FIELD_NAME = "turnable";

    public MutableTokenSerializer() {
        super();
    }

    @Override
    protected TokenBuilder<?,T> customizeDeserialization(TokenBuilder<?,T> builder, JsonNode json) {
        builder = super.customizeDeserialization(builder, json);
        if (json.has(DIRECTION_FIELD_NAME)) {
             String direction = json.get(DIRECTION_FIELD_NAME).asText();
             builder = ((MutableTokenBuilder) builder).withDirection(Direction.valueOf(direction));
         }
         boolean movable = json.has(MOVABLE_FIELD_NAME) && json.get(MOVABLE_FIELD_NAME).asBoolean();
         boolean turnable = json.has(MOVABLE_FIELD_NAME) && json.get(MOVABLE_FIELD_NAME).asBoolean();

        builder = ((MutableTokenBuilder) builder).withMutability(movable,turnable);
        return builder;
    }

    protected void customizeSerialization(ObjectNode node, Token token) {
        super.customizeSerialization(node, token);
        MutableToken mutableToken = (MutableToken) token;
        node.put(DIRECTION_FIELD_NAME, mutableToken.getDirection().toString());
        node.put(MOVABLE_FIELD_NAME, mutableToken.isMovable());
        node.put(TURNABLE_FIELD_NAME, mutableToken.isTurnable());
    }

    protected abstract MutableTokenBuilder<?, T> instantiateBuilder(JsonNode json);

}


