package model.persistence.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.Token;
import model.persistence.serializer.token.base.TokenSerializer;
import model.persistence.serializer.util.TokenSerializerRegistry;
import model.persistence.serializer.util.FieldNameRegistry;

import java.util.ArrayList;
import java.util.List;

public class LevelSerializer implements ISerializer<Level> {

    private final TokenSerializerRegistry tokenSerializerRegistry = new TokenSerializerRegistry();

    public ObjectNode serialize(Level level) {
        // serialize basic level information
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put(FieldNameRegistry.ID_FIELD_NAME, level.getId());
        node.put(FieldNameRegistry.WIDTH_FIELD_NAME, level.getBoard().getWidth());
        node.put(FieldNameRegistry.HEIGHT_FIELD_NAME, level.getBoard().getHeight());
        node.put(FieldNameRegistry.REQUIRED_TARGET_NUMBER_FIELD_NAME, level.getRequiredTargetNumber());
        node.put(FieldNameRegistry.CURRENT_TARGET_NUMBER_FIELD_NAME, level.getCurrentTargetNumber());
        node.put(FieldNameRegistry.COMPLETE_FIELD_NAME, level.isComplete());

        // serialize all tokens
        ArrayNode tokensArray = node.putArray(FieldNameRegistry.TOKENS_FIELD_NAME);
        for (Token token : level.getTokens()) {
            TokenSerializer<Token> tokenSerializer =
                    (TokenSerializer<Token>) tokenSerializerRegistry.getByClass(token.getClass());
            ObjectNode tokenJson = tokenSerializer.serialize(token);
            tokensArray.add(tokenJson);
        }

        return node;
    }

    public Level deserialize(ObjectNode json) {
        // deserialize basic level information
        int id = json.get(FieldNameRegistry.ID_FIELD_NAME).asInt();
        int boardWidth = json.get(FieldNameRegistry.WIDTH_FIELD_NAME).asInt();
        int boardHeight = json.get(FieldNameRegistry.HEIGHT_FIELD_NAME).asInt();
        int requiredTargetNumber = json.get(FieldNameRegistry.REQUIRED_TARGET_NUMBER_FIELD_NAME).asInt();
        // Default to 0 if not present
        int currentTargetNumber = json.has(FieldNameRegistry.CURRENT_TARGET_NUMBER_FIELD_NAME)
                ? json.get(FieldNameRegistry.CURRENT_TARGET_NUMBER_FIELD_NAME).asInt()
                : 0;
        // Default to false if not present
        boolean complete = json.has(FieldNameRegistry.CURRENT_TARGET_NUMBER_FIELD_NAME)
                ? json.get(FieldNameRegistry.COMPLETE_FIELD_NAME).asBoolean() : false;

        // create a LevelBuilder with the deserialized information
        LevelBuilder builder = new LevelBuilder(id)
                .withBoardDimensions(boardWidth, boardHeight)
                .withRequiredTargetNumber(requiredTargetNumber)
                .withCurrentTargetNumber(currentTargetNumber)
                .withComplete(complete);

        // deserialize all tokens
        List<Token> tokens = new ArrayList<>();
        for (JsonNode tokenJson : json.withArray(FieldNameRegistry.TOKENS_FIELD_NAME)) {
            String typeName = tokenJson.get(FieldNameRegistry.TYPE_FIELD_NAME).asText();
            TokenSerializer<? extends Token> tokenSerializer = tokenSerializerRegistry.getByTypeName(typeName);
            Token token = tokenSerializer.deserialize((ObjectNode) tokenJson);
            tokens.add(token);
        }
        builder = builder.withTokens(tokens);
        return builder.build();
    }
}
