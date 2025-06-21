package model.persistence.serializer;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.Token;
import model.domain.token.builder.base.TokenBuilder;
import model.persistence.serializer.token.TokenSerializer;

import java.util.Map;

public class LevelSerializer implements ISerializer<Level> {

    private final String ID_FIELD_NAME = "id";
    private final String WIDTH_FIELD_NAME = "width";
    private final String HEIGHT_FIELD_NAME = "height";
    private final String REQUIRED_TARGET_NUMBER_FIELD_NAME = "requiredTargetNumber";
    private final String CURRENT_TARGET_NUMBER_FIELD_NAME = "currentTargetNumber";
    private final String COMPLETE_FIELD_NAME = "complete";

    private final TokenSerializerRegistry tokenSerializerRegistry;

    public LevelSerializer(TokenSerializerRegistry tokenSerializerRegistry) {
        this.tokenSerializerRegistry = new TokenSerializerRegistry();
    }

    public ObjectNode serialize(Level level) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put(ID_FIELD_NAME, level.getId());
        node.put(WIDTH_FIELD_NAME, level.getBoard().getWidth());
        node.put(HEIGHT_FIELD_NAME, level.getBoard().getHeight());
        node.put(REQUIRED_TARGET_NUMBER_FIELD_NAME, level.getRequiredTargetNumber());
        node.put(CURRENT_TARGET_NUMBER_FIELD_NAME, level.getCurrentTargetNumber());
        node.put(COMPLETE_FIELD_NAME, level.isComplete());

        // serialize all tokens
        ArrayNode tokensArray = node.putArray("tokens");
        for (Token token : level.getTokens()) {
            TokenSerializer<Token> serializer =
                    (TokenSerializer<Token>) tokenSerializerRegistry.getByClass(token.getClass());
            ObjectNode tokenJson = serializer.serialize(token);
            tokensArray.add(tokenJson);
        }

        return node;
    }

    public Level deserialize(ObjectNode json) {
        int id = json.get("id").asInt();
        Level level = new LevelBuilder(id).build();
        return level;
    }
}
