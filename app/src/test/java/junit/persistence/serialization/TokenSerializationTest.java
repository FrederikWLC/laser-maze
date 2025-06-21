package junit.persistence.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.token.impl.DoubleMirrorToken;
import model.domain.token.impl.TargetMirrorToken;
import model.persistence.serializer.token.impl.DoubleMirrorTokenSerializer;
import model.persistence.serializer.token.impl.TargetMirrorTokenSerializer;
import org.junit.jupiter.api.Test;

public class TokenSerializationTest {
    @Test
    public void deserializesMutableTokenWithCorrectMutability() throws JsonProcessingException {
        String json = "{ \"type\": \"DoubleMirrorToken\", \"movable\": \"false\", \"turnable\": \"false\" }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        DoubleMirrorToken token = new DoubleMirrorTokenSerializer().deserialize((ObjectNode) node);
        assert !token.isMovable() : "DoubleMirrorToken should not be movable";
        assert !token.isTurnable() : "DoubleMirrorToken should not be turnable";
    }

    @Test
    public void deserializesTargetTokenWithCorrectIsRequiredTarget() throws JsonProcessingException {
        String json = "{ \"type\": \"TargetMirrorToken\", \"movable\": \"false\", \"turnable\": \"false\" }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        TargetMirrorToken token = new TargetMirrorTokenSerializer().deserialize((ObjectNode) node);
        assert !token.isRequiredTarget() : "TargetMirrorToken should not be required target by default";
    }
}
