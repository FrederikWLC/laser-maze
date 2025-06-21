package model.persistence.storage;

import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.level.Level;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class DefaultLevelLoader extends LevelPathResolver {
    protected final ObjectMapper mapper = new ObjectMapper();

    public Path getFolderPath() {
        return pathHelper.getResourcePath(getFolderName());
    }

    public JsonNode loadJSON(int id)  {
        Path levelFile = getLevelPath(id);
        try {
            String json = Files.readString(levelFile, StandardCharsets.UTF_8);
            return mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read or parse JSON for level " + id + ": " + levelFile, e);
        }
    }

    public Level load(int id) {
        JsonNode root = loadJSON(id);
        return levelSerializer.deserialize((ObjectNode) root);
    }

    public List<Integer> getAllAvailableLevelIds() {
        try {
            return Files.list(getFolderPath())
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> {
                        String fileName = path.getFileName().toString();
                        int id = Integer.parseInt(fileName.substring(0, fileName.length() - 5)); // Remove ".json"
                        return id;
                    })
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get all available level id's: " + e.getMessage(), e);
        }
    }


}
