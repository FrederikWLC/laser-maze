package model.persistence.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.domain.level.Level;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SavedLevelLoader extends DefaultLevelLoader {

    @Override
    public Path getFolderPath() {
        return pathHelper.getAppDataPath(getFolderName());
    }

}
