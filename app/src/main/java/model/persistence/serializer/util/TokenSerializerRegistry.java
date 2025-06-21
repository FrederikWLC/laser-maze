package model.persistence.serializer.util;

import model.domain.token.base.Token;
import model.persistence.serializer.token.base.TokenSerializer;
import model.persistence.serializer.token.impl.*;

import java.util.HashMap;
import java.util.Map;

public class TokenSerializerRegistry {
    private final Map<String, TokenSerializer<? extends Token>> byTypeName = new HashMap<>();
    private final Map<Class<? extends Token>, TokenSerializer<? extends Token>> byClass = new HashMap<>();

    public TokenSerializerRegistry() { // Register all token serializers, not solid, but it works (violates OCP
        register(new BeamSplitterTokenSerializer());
        register(new CellBlockerTokenSerializer());
        register(new CheckpointTokenSerializer());
        register(new DoubleMirrorTokenSerializer());
        register(new LaserTokenSerializer());
        register(new PortalTokenSerializer());
        register(new TargetMirrorTokenSerializer());
    }

    public <T extends Token> void register(TokenSerializer<T> tokenSerializer) {
        byTypeName.put(tokenSerializer.getTokenTypeName(), tokenSerializer);
        byClass.put(tokenSerializer.getTokenClass(), tokenSerializer);
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
