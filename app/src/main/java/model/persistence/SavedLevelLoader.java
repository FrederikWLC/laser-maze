package model.persistence;

import model.domain.level.Level;
import org.gradle.internal.impldep.com.google.api.client.json.Json;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SavedLevelLoader extends DefaultLevelLoader {

    @Override
    public Path getPath() {
        return pathHelper.getAppDataPath(getFolderName());
    }

    public static List<Level> loadAll() {
        List<Level> levels = new ArrayList<>();
        for (int i : new int[]{
                1, 2, 3, 4,
                17, 18, 28, 30,
                33, 34, 36, 40,
                52, 54, 58
        }) {
            levels.add(load(i));
        }
        return levels;
    }

}
