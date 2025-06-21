package junit.persistence.serialization;

import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.persistence.serializer.LevelSerializer;
import org.junit.jupiter.api.Test;

public class LevelSerializationTest {
    @Test
    public void serializeAndDeserializeLevelWithCorrectInformation() {
        int id = 999;
        int width = 6;
        int height = 6;
        int requiredTargetNumber = 10;
        int currentTargetNumber = 10;
        boolean complete = true;
        Level level = new LevelBuilder(999)
                .withBoardDimensions(6,6)
                .withComplete(complete)
                .withRequiredTargetNumber(10)
                .withCurrentTargetNumber(10)
                .build();
        ObjectNode serializedLevel = new LevelSerializer().serialize(level);
        Level deserializedLevel = new LevelSerializer().deserialize(serializedLevel);
        assert deserializedLevel.getId() == id : "ID mismatch after serialization/deserialization";
        assert deserializedLevel.getBoard().getWidth() == width: "Width mismatch after serialization/deserialization";
        assert deserializedLevel.getBoard().getHeight() == height : "Height mismatch after serialization/deserialization";
        assert deserializedLevel.getRequiredTargetNumber() == requiredTargetNumber : "Required target number mismatch after serialization/deserialization";
        assert deserializedLevel.getCurrentTargetNumber() == currentTargetNumber : "Current target number mismatch after serialization/deserialization";
        assert deserializedLevel.isComplete() == complete : "Complete status mismatch after serialization/deserialization";

    }
}
