package model.persistence.storage;

import model.domain.level.Level;
import model.domain.level.builder.LevelBuilder;
import model.domain.token.base.Token;

import java.nio.file.Path;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.persistence.util.PathHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class DefaultLevelLoader implements ILoader {
    protected final PathHelper pathHelper = new PathHelper();
    protected final ObjectMapper mapper = new ObjectMapper();

    public String getFolderName() {
        return "levels";
    }

    public Path getPath() {
        return pathHelper.getResourcePath(getFolderName());
    }

    public JsonNode loadJSON(int id) {
        Path file = getPath().resolve(id+".dat");
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            return mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or parse JSON for level " + id + ": " + file, e);
        }
    }

    public Level load(int id) {
        JsonNode root = loadJSON(id);
        JsonNode tokensNode = root.get("textures/tokens");

        List<Token> tokens = LevelStorage.getTokensFor(id);
        int targetNumberRequired = LevelStorage.getRequiredTargetNumberFor(id);

        return new LevelBuilder(id)
                .withTokens(tokens)
                .withRequiredTargetNumber(targetNumberRequired)
                .build();
    }
}
