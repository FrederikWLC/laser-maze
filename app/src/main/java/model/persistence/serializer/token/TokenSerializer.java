package model.persistence.serializer.token;

import model.domain.token.base.Token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.token.builder.base.TokenBuilder;
import model.persistence.serializer.ISerializer;

import java.lang.reflect.ParameterizedType;

public abstract class TokenSerializer<T extends Token> implements ISerializer<T> {


    private final String TYPE_FIELD_NAME = "type";
    private final String X_FIELD_NAME = "x";
    private final String Y_FIELD_NAME = "y";


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
        if (json.has(X_FIELD_NAME) && json.has(Y_FIELD_NAME)) {
            builder = builder.withPosition(
                    json.get(X_FIELD_NAME).asInt(),
                    json.get(Y_FIELD_NAME).asInt()
            );
        }
        return builder;
    }

    protected void customizeSerialization(ObjectNode node, Token token) {
        // Default implementation does nothing, can be overridden by subclasses
        node.put(TYPE_FIELD_NAME, token.getClass().getSimpleName());
        if (token.getPosition() != null) {
            node.put(X_FIELD_NAME , token.getPosition().getX());
            node.put(Y_FIELD_NAME, token.getPosition().getY());
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
