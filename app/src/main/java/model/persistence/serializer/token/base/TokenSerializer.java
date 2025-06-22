package model.persistence.serializer.token.base;

import model.domain.token.base.Token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.token.builder.base.TokenBuilder;
import model.persistence.serializer.util.FieldNameRegistry;
import model.persistence.serializer.Serializer;

import java.lang.reflect.ParameterizedType;

public abstract class TokenSerializer<T extends Token> extends Serializer<T> {

    public ObjectNode serialize(T token) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        customizeSerialization(node, token);
        return node;
    }

    public T deserialize(ObjectNode json) {
        TokenBuilder<?,T> builder = instantiateBuilder(json);
        builder = customizeDeserialization(builder, json);
        return builder.build();
    }

    protected TokenBuilder<?,T> customizeDeserialization(TokenBuilder<?,T> builder, JsonNode json) {
        // Default null
        Integer x = json.has(FieldNameRegistry.X_FIELD_NAME) ? json.get(FieldNameRegistry.X_FIELD_NAME).asInt() : null;
        Integer y = json.has(FieldNameRegistry.Y_FIELD_NAME) ? json.get(FieldNameRegistry.Y_FIELD_NAME).asInt() : null;
        if (x != null && y != null) {
            builder = builder.withPosition(x, y);
        }
        return builder;
    }

    protected void customizeSerialization(ObjectNode node, Token token) {
        // Default implementation does nothing, can be overridden by subclasses
        node.put(FieldNameRegistry.TYPE_FIELD_NAME, token.getClass().getSimpleName());
        if (token.isPlaced()) {
            node.put(FieldNameRegistry.X_FIELD_NAME , token.getPosition().getX());
            node.put(FieldNameRegistry.Y_FIELD_NAME, token.getPosition().getY());
        }
    }

    protected abstract TokenBuilder<?,T> instantiateBuilder(JsonNode json);

    @SuppressWarnings("unchecked")
    public Class<T> getTokenClass() {
        ParameterizedType parent = (ParameterizedType)
                getClass().getGenericSuperclass();
        return (Class<T>) parent.getActualTypeArguments()[0];
    }

    public String getTokenTypeName() {
        return getTokenClass().getSimpleName();
    }



}
