package model.persistence.serializer;

import model.domain.token.base.Token;
import model.persistence.serializer.token.TokenSerializer;

import java.util.HashMap;
import java.util.Map;

public class TokenSerializerRegistry {
    private final Map<String, TokenSerializer<? extends Token>> byTypeName = new HashMap<>();
    private final Map<Class<? extends Token>, TokenSerializer<? extends Token>> byClass = new HashMap<>();

    public <T extends Token> void register(TokenSerializer<T> ser) {
        byTypeName.put(ser.getTokenTypeName(), ser);
        byClass.put(ser.getTokenClass(), ser);
    }

    // Lookup for serialization
    public TokenSerializer<? extends Token> getByClass(Class<? extends Token> cls) {
        return byClass.get(cls);
    }

    // Lookup for deserialization
    public TokenSerializer<? extends Token> getByTypeName(String typeName) {
        return byTypeName.get(typeName);
    }

}
